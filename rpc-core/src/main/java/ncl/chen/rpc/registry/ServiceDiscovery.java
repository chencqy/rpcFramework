package ncl.chen.rpc.registry;

import java.net.InetSocketAddress;

/**
 * @author: Qiuyu
 */
public interface ServiceDiscovery {
    /**
     * Get service by name
     * @param serviceName name of service
     * @return address of service
     */
    InetSocketAddress lookupService(String serviceName);
}
