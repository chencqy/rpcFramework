package ncl.chen.rpc.test;

import ncl.chen.rpc.annotation.ServiceScan;
import ncl.chen.rpc.serializer.CommonSerializer;
import ncl.chen.rpc.transport.RpcServer;
import ncl.chen.rpc.transport.netty.server.NettyServer;

/**
 * @author: Qiuyu
 */
@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9000,
                CommonSerializer.DEFAULT_SERIALIZER);
        server.start();
    }
}
