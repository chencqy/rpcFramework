package ncl.chen.rpc.test;

import ncl.chen.rpc.api.ByeService;
import ncl.chen.rpc.api.HelloService;
import ncl.chen.rpc.netty.server.NettyServer;
import ncl.chen.rpc.registry.DefaultServiceRegistry;
import ncl.chen.rpc.registry.ServiceRegistry;
import ncl.chen.rpc.RpcServer;

/**
 * @author: Qiuyu
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ByeService byeService = new ByeServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        serviceRegistry.register(byeService);
        RpcServer rpcServer = new NettyServer();
        rpcServer.start(9000);
    }
}
