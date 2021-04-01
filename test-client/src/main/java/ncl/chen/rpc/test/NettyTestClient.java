package ncl.chen.rpc.test;

import ncl.chen.rpc.api.ByeService;
import ncl.chen.rpc.api.HelloObject;
import ncl.chen.rpc.api.HelloService;
import ncl.chen.rpc.serializer.KryoSerializer;
import ncl.chen.rpc.transport.RpcClient;
import ncl.chen.rpc.transport.RpcClientProxy;
import ncl.chen.rpc.transport.netty.client.NettyClient;

/**
 * Test consumer (client)
 * @author: Qiuyu
 */
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient();
        client.setSerializer(new KryoSerializer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        ByeService byeService = proxy.getProxy(ByeService.class);
        HelloObject hello = new HelloObject(12, "Hello");
//        ByeObject bye = new ByeObject(10, "Bye");
        String resHello = helloService.hello(hello);
//        String resBye = byeService.bye(bye);

        System.out.println(resHello);
//        System.out.println(resBye);
    }
}
