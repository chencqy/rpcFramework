package ncl.chen.rpc.test;

import ncl.chen.rpc.api.HelloService;
import ncl.chen.rpc.server.RpcServer;

/**
 * @author: Qiuyu
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9000);
    }
}
