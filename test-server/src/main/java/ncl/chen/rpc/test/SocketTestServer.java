package ncl.chen.rpc.test;

import ncl.chen.rpc.api.ByeService;
import ncl.chen.rpc.api.HelloService;
import ncl.chen.rpc.serializer.KryoSerializer;
import ncl.chen.rpc.transport.socket.server.SocketServer;

/**
 * @author: Qiuyu
 */
public class SocketTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ByeService byeService = new ByeServiceImpl();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9001);
        socketServer.setSerializer(new KryoSerializer());
        socketServer.publishService(helloService, HelloService.class);
//        socketServer.publishService(byeService, ByeService.class);

    }
}
