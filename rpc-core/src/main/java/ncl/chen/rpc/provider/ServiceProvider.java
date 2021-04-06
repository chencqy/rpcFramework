package ncl.chen.rpc.provider;

/**
 * Save and provide service objects
 * @author: Qiuyu
 */
public interface ServiceProvider {

    /**
     * Add a service provider
     * @param service
     * @param serviceName
     * @param <T>
     */
    <T> void addServiceProvider(T service, String serviceName);

    /**
     * Get service provider by name of service
     * @param serviceName
     * @return
     */
    Object getServiceProvider(String serviceName);
}
