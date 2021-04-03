package ncl.chen.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @author: Qiuyu
 */
public interface LoadBalancer {
    /**
     * Override this method to implement load balancing algorithm
     * @param instances All services
     * @return service
     */
    Instance select(List<Instance> instances);
}
