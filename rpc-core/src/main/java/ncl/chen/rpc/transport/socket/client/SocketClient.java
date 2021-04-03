package ncl.chen.rpc.transport.socket.client;

import ncl.chen.rpc.entity.RpcRequest;
import ncl.chen.rpc.entity.RpcResponse;
import ncl.chen.rpc.enumeration.ResponseCode;
import ncl.chen.rpc.enumeration.RpcError;
import ncl.chen.rpc.exception.RpcException;
import ncl.chen.rpc.loadbalancer.LoadBalancer;
import ncl.chen.rpc.loadbalancer.RandomLoadBalancer;
import ncl.chen.rpc.registry.NacosServiceDiscovery;
import ncl.chen.rpc.registry.ServiceDiscovery;
import ncl.chen.rpc.serializer.CommonSerializer;
import ncl.chen.rpc.transport.RpcClient;
import ncl.chen.rpc.transport.util.ObjectReader;
import ncl.chen.rpc.transport.util.ObjectWriter;
import ncl.chen.rpc.util.RpcMessageChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import static ncl.chen.rpc.serializer.CommonSerializer.DEFAULT_SERIALIZER;

/**
 * @author: Qiuyu
 */
public class SocketClient implements RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    private final ServiceDiscovery serviceDiscovery;

    private CommonSerializer serializer;

    public SocketClient() {
        this(DEFAULT_SERIALIZER, new RandomLoadBalancer());
    }
    public SocketClient(LoadBalancer loadBalancer) {
        this(DEFAULT_SERIALIZER, loadBalancer);
    }
    public SocketClient(Integer serializer) {
        this(serializer, new RandomLoadBalancer());
    }

    public SocketClient(Integer serializer, LoadBalancer loadBalancer) {
        this.serviceDiscovery = new NacosServiceDiscovery(loadBalancer);
        this.serializer = CommonSerializer.getByCode(serializer);
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            logger.error("Serializer is not set");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectWriter.writeObject(objectOutputStream, rpcRequest, serializer);
            Object obj = ObjectReader.readObject(objectInputStream);
            RpcResponse rpcResponse = (RpcResponse) obj;
            if(rpcResponse == null) {
                logger.error("Failed to call the service，service：{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE,
                        "service:" + rpcRequest.getInterfaceName());
            }
            if(rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() !=
                    ResponseCode.SUCCESS.getCode()) {
                logger.error("Failed to call the service, service: {}, response:{}",
                        rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE,
                        " service:" + rpcRequest.getInterfaceName());
            }
            RpcMessageChecker.check(rpcRequest, rpcResponse);
            return rpcResponse;
        } catch (IOException e) {
            logger.error("An error occurred during the call：", e);
            throw new RpcException("Failed to call the service: ", e);
        }

    }
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
