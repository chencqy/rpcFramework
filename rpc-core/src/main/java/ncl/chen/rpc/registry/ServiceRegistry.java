package ncl.chen.rpc.registry;

import java.net.InetSocketAddress;

/**
 * Interface of Service Registry
 * @author: Qiuyu
 */
public interface ServiceRegistry {

    /**
     * Register a service into the registry
     * @param serviceName name of service
     * @param inetSocketAddress address
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

    /**
     * Get the service by its name
     * @param serviceName name of service
     * @return address
     */
    InetSocketAddress lookupService(String serviceName);
}
