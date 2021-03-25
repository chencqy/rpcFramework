package ncl.chen.rpc.test;

import ncl.chen.rpc.api.HelloObject;
import ncl.chen.rpc.api.HelloService;
import ncl.chen.rpc.client.RpcClientProxy;

/**
 * Test consumer (client)
 * @author: Qiuyu
 */
public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
