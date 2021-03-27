package ncl.chen.rpc.registry;

/**
 * Interface of Service Registry
 * @author: Qiuyu
 */
public interface ServiceRegistry {

    /**
     * Register a service into the registry
     * @param service
     * @param <T>
     */
    <T> void register(T service);

    /**
     * Get the service from registry by name
     * @param serviceName the name of service
     * @return the service
     */
    Object getService(String serviceName);
}
