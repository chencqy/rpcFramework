package ncl.chen.rpc.test;

import ncl.chen.rpc.api.ByeService;
import ncl.chen.rpc.api.HelloService;
import ncl.chen.rpc.registry.DefaultServiceRegistry;
import ncl.chen.rpc.registry.ServiceRegistry;
import ncl.chen.rpc.socket.server.SocketServer;

/**
 * @author: Qiuyu
 */
public class SocketTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ByeService byeService = new ByeServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        serviceRegistry.register(byeService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9000);
    }
}
