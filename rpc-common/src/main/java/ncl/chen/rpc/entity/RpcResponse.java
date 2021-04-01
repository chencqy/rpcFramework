package ncl.chen.rpc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import ncl.chen.rpc.enumeration.ResponseCode;

import java.io.Serializable;

/**
 * @author: Qiuyu
 */

@Data
@NoArgsConstructor
public class RpcResponse<T> implements Serializable {
    private static final long serialVersionUID = 5485903335130406396L;

    /**
     * Respond to the corresponding request id
     */
    private String requestId;
    /**
     * Response status code
     */
    private Integer statusCode;
    /**
     * Response status supplementary information
     */
    private String message;
    /**
     * Response data
     */
    private T data;

    public static <T> RpcResponse<T> success(T data, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setRequestId(requestId);
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setRequestId(requestId);
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
}

