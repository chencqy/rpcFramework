package ncl.chen.rpc.netty.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import ncl.chen.rpc.RequestHandler;
import ncl.chen.rpc.entity.RpcRequest;
import ncl.chen.rpc.entity.RpcResponse;
import ncl.chen.rpc.registry.DefaultServiceRegistry;
import ncl.chen.rpc.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Qiuyu
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private static RequestHandler requestHandler;

    private static ServiceRegistry serviceRegistry;

    static {
        requestHandler = new RequestHandler();
        serviceRegistry = new DefaultServiceRegistry();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try {
            logger.info("Server received the request: {}", msg);
            String interfaceName = msg.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            Object result = requestHandler.handle(msg, service);
            ChannelFuture future = ctx.writeAndFlush(RpcResponse.success(result));
            future.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("An error occurred during the process of handle the call");
        cause.printStackTrace();
        ctx.close();
    }
}
