package ncl.chen.rpc.transport;

import ncl.chen.rpc.annotation.Service;
import ncl.chen.rpc.annotation.ServiceScan;
import ncl.chen.rpc.enumeration.RpcError;
import ncl.chen.rpc.exception.RpcException;
import ncl.chen.rpc.provider.ServiceProvider;
import ncl.chen.rpc.registry.ServiceRegistry;
import ncl.chen.rpc.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * @author: Qiuyu
 */
public abstract class AbstractRpcServer implements RpcServer{

    protected static final Logger logger = LoggerFactory.getLogger(AbstractRpcServer.class);

    protected String host;
    protected int port;

    protected ServiceProvider serviceProvider;
    protected ServiceRegistry serviceRegistry;

    public void scanService(){
        String mainClass = ReflectUtil.getStackTrace();
        Class<?> startClass;
        try {
             startClass = Class.forName(mainClass);
             if (!startClass.isAnnotationPresent(ServiceScan.class)) {
                 logger.error("The startup class lacks @ServiceScan annotation");
                 throw new RpcException(RpcError.SERVICE_SCAN_PACKAGE_NOT_FOUND);
             }
        } catch (ClassNotFoundException e) {
            logger.error("An unknown error occurred");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }
        String basePackage = startClass.getAnnotation(ServiceScan.class).value();
        if ("".equals(basePackage)) {
            basePackage = mainClass.substring(0, mainClass.lastIndexOf("."));
        }
        Set<Class<?>> classes = ReflectUtil.getClasses(basePackage);
        if (classes.isEmpty()) {
            logger.error("An unknown error occurred");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Service.class)) {
                String serviceName = clazz.getAnnotation(Service.class).name();
                Object obj;
                try {
                    obj = clazz.newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    logger.error("An error occurred while creating "+ clazz);
                    continue;
                }
                if ("".equals(serviceName)) {
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> oneInterface : interfaces) {
                        publishService(obj, oneInterface.getCanonicalName());
                    }
                } else {
                    publishService(obj, serviceName);
                }
            }
        }
    }

    @Override
    public <T> void publishService(T service, String serviceName) {
        serviceProvider.addServiceProvider(service, serviceName);
        serviceRegistry.register(serviceName, new InetSocketAddress(host, port));
    }

}
