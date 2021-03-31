package ncl.chen.rpc.entity;

import lombok.Data;
import ncl.chen.rpc.enumeration.ResponseCode;

import java.io.Serializable;

/**
 * @author: Qiuyu
 */

@Data
public class RpcResponse<T> implements Serializable {
    private static final long serialVersionUID = 5485903335130406396L;

    public RpcResponse() {}

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

    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }
    public static <T> RpcResponse<T> fail(ResponseCode code) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
}

