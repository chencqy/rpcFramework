package ncl.chen.rpc.test;

import ncl.chen.rpc.api.ByeService;
import ncl.chen.rpc.api.HelloService;
import ncl.chen.rpc.serializer.KryoSerializer;
import ncl.chen.rpc.transport.netty.server.NettyServer;

/**
 * @author: Qiuyu
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ByeService byeService = new ByeServiceImpl();
        NettyServer server = new NettyServer("127.0.0.1", 9000);
        server.setSerializer(new KryoSerializer());
        server.publishService(helloService, HelloService.class);
//        server.publishService(byeService, ByeService.class);
    }
}
