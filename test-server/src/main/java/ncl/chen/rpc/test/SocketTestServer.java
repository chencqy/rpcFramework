package ncl.chen.rpc.test;

import ncl.chen.rpc.annotation.ServiceScan;
import ncl.chen.rpc.serializer.CommonSerializer;
import ncl.chen.rpc.transport.RpcServer;
import ncl.chen.rpc.transport.socket.server.SocketServer;

/**
 * @author: Qiuyu
 */
@ServiceScan
public class SocketTestServer {
    public static void main(String[] args) {
        RpcServer socketServer = new SocketServer("127.0.0.1", 9001,
                CommonSerializer.DEFAULT_SERIALIZER);
        socketServer.start();
    }
}
