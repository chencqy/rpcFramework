package ncl.chen.rpc.transport.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import ncl.chen.rpc.entity.RpcRequest;
import ncl.chen.rpc.entity.RpcResponse;
import ncl.chen.rpc.enumeration.RpcError;
import ncl.chen.rpc.exception.RpcException;
import ncl.chen.rpc.loadbalancer.LoadBalancer;
import ncl.chen.rpc.loadbalancer.RandomLoadBalancer;
import ncl.chen.rpc.registry.NacosServiceDiscovery;
import ncl.chen.rpc.registry.ServiceDiscovery;
import ncl.chen.rpc.serializer.CommonSerializer;
import ncl.chen.rpc.transport.RpcClient;
import ncl.chen.rpc.util.SingletonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

import static ncl.chen.rpc.serializer.CommonSerializer.DEFAULT_SERIALIZER;

/**
 * @author: Qiuyu
 */
public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private static final Bootstrap bootstrap;
    private static final EventLoopGroup group;
    private final ServiceDiscovery serviceDiscovery;

    private CommonSerializer serializer;
    private final UnprocessedRequests unprocessedRequests;

    static {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class);
    }

    public NettyClient() {
        this(DEFAULT_SERIALIZER, new RandomLoadBalancer());
    }

    public NettyClient(LoadBalancer loadBalancer) {
        this(DEFAULT_SERIALIZER, loadBalancer);
    }

    public NettyClient(Integer serializer) {
        this(serializer, new RandomLoadBalancer());
    }

    public NettyClient(Integer serializer, LoadBalancer loadBalancer) {
        this.serviceDiscovery = new NacosServiceDiscovery(loadBalancer);
        this.serializer = CommonSerializer.getByCode(serializer);
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if(serializer == null) {
            logger.error("Serializer is not set");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        CompletableFuture<RpcResponse> resultFuture = new CompletableFuture<>();
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
        Channel channel = ChannelProvider.get(inetSocketAddress, serializer);
        if (!channel.isActive()) {
            group.shutdownGracefully();
            return null;
        }
        unprocessedRequests.put(rpcRequest.getRequestId(), resultFuture);
        channel.writeAndFlush(rpcRequest).addListener((ChannelFutureListener) future1 -> {
            if (future1.isSuccess()) {
                logger.info("Client sent the message: {}", rpcRequest.toString());
            } else {
                future1.channel().close();
                resultFuture.completeExceptionally(future1.cause());
                logger.error("Errors occurred when sending message", future1.cause());
            }
        });
        return resultFuture;
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }

}
