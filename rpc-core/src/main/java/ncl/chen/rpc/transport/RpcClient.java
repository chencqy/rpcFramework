package ncl.chen.rpc.transport;

import ncl.chen.rpc.entity.RpcRequest;

/**
 * @author: Qiuyu
 */
public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);

}
