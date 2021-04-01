package ncl.chen.rpc.transport;

import ncl.chen.rpc.serializer.CommonSerializer;

/**
 * The provider of the remote method call (server)
 * @author: Qiuyu
 */
public interface RpcServer {

    void start();

    /**
     * Set serializer
     * @param serializer
     */
    void setSerializer(CommonSerializer serializer);

    /**
     * Register a service to Nacos
     * @param service
     * @param serviceClass
     * @param <T>
     */
    <T> void publishService(Object service, Class<T> serviceClass);
}
