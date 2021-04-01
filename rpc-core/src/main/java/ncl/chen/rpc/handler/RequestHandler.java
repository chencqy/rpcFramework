package ncl.chen.rpc.handler;

import ncl.chen.rpc.entity.RpcRequest;
import ncl.chen.rpc.entity.RpcResponse;
import ncl.chen.rpc.enumeration.ResponseCode;
import ncl.chen.rpc.provider.ServiceProvider;
import ncl.chen.rpc.provider.ServiceProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @author: Qiuyu
 */
public class RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final ServiceProvider serviceProvider;

    static {
        serviceProvider = new ServiceProviderImpl();
    }

    public Object handle(RpcRequest rpcRequest) {
        Object result = null;
        Object service = serviceProvider.getServiceProvider(rpcRequest.getInterfaceName());
        try {
            result = invokeTargetMethod(rpcRequest, service);
            logger.info("Service: {} successfully called method: {}",
                        rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (IllegalAccessException |InvocationTargetException e) {
            logger.error("An error occurred when invoking or sending");
        }
        return result;
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws
            IllegalAccessException, InvocationTargetException {
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND, rpcRequest.getRequestId());
        }
        return method.invoke(service, rpcRequest.getParameters());

    }
}
