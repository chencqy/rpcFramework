package ncl.chen.rpc.test;

import ncl.chen.rpc.api.ByeService;
import ncl.chen.rpc.api.HelloService;
import ncl.chen.rpc.serializer.CommonSerializer;
import ncl.chen.rpc.transport.RpcClient;
import ncl.chen.rpc.transport.RpcClientProxy;
import ncl.chen.rpc.transport.netty.client.NettyClient;

/**
 * Test consumer (client)
 * @author: Qiuyu
 */
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient(CommonSerializer.HESSIAN_SERIALIZER);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        ByeService byeService = proxy.getProxy(ByeService.class);
        String resHello = helloService.hello("Qiuyu");
        String resBye = byeService.bye("Qiuyu");
        System.out.println(resHello);
        System.out.println(resBye);
    }
}
