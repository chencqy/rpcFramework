package ncl.chen.rpc.test;

import ncl.chen.rpc.RpcClientProxy;
import ncl.chen.rpc.api.ByeObject;
import ncl.chen.rpc.api.ByeService;
import ncl.chen.rpc.api.HelloObject;
import ncl.chen.rpc.api.HelloService;
import ncl.chen.rpc.socket.client.SocketClient;

/**
 * @author: Qiuyu
 */
public class SocketTestClient {
    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9000);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        ByeService byeService = proxy.getProxy(ByeService.class);
        HelloObject hello = new HelloObject(12, "Hello");
        ByeObject bye = new ByeObject(10, "Bye");
        String resHello = helloService.hello(hello);
        String resBye = byeService.bye(bye);

        System.out.println(resHello);
        System.out.println(resBye);
    }
}
