package ncl.chen.rpc.transport.socket.server;

import ncl.chen.rpc.handler.RequestHandler;
import ncl.chen.rpc.enumeration.RpcError;
import ncl.chen.rpc.exception.RpcException;
import ncl.chen.rpc.provider.ServiceProvider;
import ncl.chen.rpc.provider.ServiceProviderImpl;
import ncl.chen.rpc.registry.NacosServiceRegistry;
import ncl.chen.rpc.registry.ServiceRegistry;
import ncl.chen.rpc.serializer.CommonSerializer;
import ncl.chen.rpc.transport.RpcServer;
import ncl.chen.rpc.util.ThreadPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @author: Qiuyu
 */
public class SocketServer implements RpcServer{

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private final ExecutorService threadPool;;
    private final String host;
    private final int port;
    private CommonSerializer serializer;
    private RequestHandler requestHandler = new RequestHandler();
    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;

    public SocketServer(String host, int port) {
        this.host = host;
        this.port = port;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
    }

    @Override
    public <T> void publishService(Object service, Class<T> serviceClass) {
        if(serializer == null) {
            logger.error("Serializer is not set");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        serviceProvider.addServiceProvider(service);
        serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server is starting...");
            Socket socket;
            while((socket = serverSocket.accept()) != null) {
                logger.info("The client connects successfully: {}:{}",
                        socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("An error occurred during the connection：", e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
