package ncl.chen.rpc.transport.socket.server;

import ncl.chen.rpc.handler.RequestHandler;
import ncl.chen.rpc.provider.ServiceProviderImpl;
import ncl.chen.rpc.registry.NacosServiceRegistry;
import ncl.chen.rpc.serializer.CommonSerializer;
import ncl.chen.rpc.transport.AbstractRpcServer;
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
public class SocketServer extends AbstractRpcServer {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private final ExecutorService threadPool;;
    private final CommonSerializer serializer;
    private final RequestHandler requestHandler = new RequestHandler();

    public SocketServer(String host, int port) {
        this(host, port, DEFAULT_SERIALIZER);
    }

    public SocketServer(String host, int port, Integer serializer) {
        this.host = host;
        this.port = port;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        serviceRegistry = new NacosServiceRegistry();
        serviceProvider = new ServiceProviderImpl();
        this.serializer = CommonSerializer.getByCode(serializer);
        scanService();
    }

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(host, port));
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
}
