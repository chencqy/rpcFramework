package ncl.chen.rpc.transport.socket.server;

import ncl.chen.rpc.handler.RequestHandler;
import ncl.chen.rpc.entity.RpcRequest;
import ncl.chen.rpc.entity.RpcResponse;
import ncl.chen.rpc.registry.ServiceRegistry;
import ncl.chen.rpc.serializer.CommonSerializer;
import ncl.chen.rpc.transport.util.ObjectReader;
import ncl.chen.rpc.transport.util.ObjectWriter;
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
    private CommonSerializer serializer;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry, CommonSerializer serializer) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
        this.serializer = serializer;
    }

    @Override
    public void run() {
        try (ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) ObjectReader.readObject(is);
            Object result = requestHandler.handle(rpcRequest);
            RpcResponse<Object> response = RpcResponse.success(result, rpcRequest.getRequestId());
            ObjectWriter.writeObject(os, response, serializer);
        } catch (IOException e) {
            logger.error("An error occurred when invoking：", e);
        }

    }
}
