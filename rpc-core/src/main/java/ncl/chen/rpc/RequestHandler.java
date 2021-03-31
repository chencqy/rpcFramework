package ncl.chen.rpc;

import ncl.chen.rpc.entity.RpcRequest;
import ncl.chen.rpc.entity.RpcResponse;
import ncl.chen.rpc.enumeration.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @author: Qiuyu
 */
public class RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public Object handle(RpcRequest rpcRequest, Object service) {
        Object result = null;
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
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND);
        }
        return method.invoke(service, rpcRequest.getParameters());

    }
}
