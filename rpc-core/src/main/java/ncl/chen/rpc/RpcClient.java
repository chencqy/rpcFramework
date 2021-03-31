package ncl.chen.rpc;

import ncl.chen.rpc.entity.RpcRequest;

/**
 * @author: Qiuyu
 */
public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);

}
