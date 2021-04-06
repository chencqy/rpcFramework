package ncl.chen.rpc.transport;

import ncl.chen.rpc.serializer.CommonSerializer;

/**
 * The provider of the remote method call (server)
 * @author: Qiuyu
 */
public interface RpcServer {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    void start();

    /**
     * Register a service to Nacos
     * @param service
     * @param serviceName
     * @param <T>
     */
    <T> void publishService(T service, String serviceName);
}
