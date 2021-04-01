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
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("Service does not implement any interface"),
    UNKNOWN_PROTOCOL("Unknown protocol"),
    UNKNOWN_SERIALIZER("Unknown (de)serializer"),
    UNKNOWN_PACKAGE_TYPE("Unknown package type"),
    SERIALIZER_NOT_FOUND("Serializer not found"),
    RESPONSE_NOT_MATCH("The response does not match the request"),
    FAILED_TO_CONNECT_TO_SERVICE_REGISTRY("Failed to connect to the registry"),
    REGISTER_SERVICE_FAILED("Failed to register service"),
    CLIENT_CONNECT_SERVER_FAILURE("The client failed to connect to the server");


    private final String message;
}
