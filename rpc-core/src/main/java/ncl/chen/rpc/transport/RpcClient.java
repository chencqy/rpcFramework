package ncl.chen.rpc.transport;

import ncl.chen.rpc.entity.RpcRequest;
import ncl.chen.rpc.serializer.CommonSerializer;

/**
 * @author: Qiuyu
 */
public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);

    void setSerializer(CommonSerializer serializer);
}
