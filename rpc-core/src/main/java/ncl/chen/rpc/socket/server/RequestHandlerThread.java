package ncl.chen.rpc.socket.server;

import ncl.chen.rpc.entity.RpcRequest;
import ncl.chen.rpc.entity.RpcResponse;
import ncl.chen.rpc.registry.ServiceRegistry;
import ncl.chen.rpc.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author: Qiuyu
 */
public class RequestHandlerThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void run() {
        try (ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) is.readObject();
            String interfaceName = rpcRequest.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            Object result = requestHandler.handle(rpcRequest, service);
            os.writeObject(RpcResponse.success(result));
            os.flush();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("An error occurred when invoking：", e);
        }

    }
}
