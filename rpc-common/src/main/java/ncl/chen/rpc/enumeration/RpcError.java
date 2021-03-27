package ncl.chen.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Errors during RPC call
 * @author: Qiuyu
 */
@AllArgsConstructor
@Getter
public enum RpcError {
    SERVICE_INVOCATION_FAILURE("Failed to call the service"),
    SERVICE_NOT_FOUND("Service not found"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("Service does not implement any interface");

    private final String message;
}
