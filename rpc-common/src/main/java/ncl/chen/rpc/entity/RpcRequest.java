package ncl.chen.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: Qiuyu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 6286211784922808495L;

    /**
     * Request id
     */
    private String requestId;
    /**
     * The name of the interface to be called
     */
    private String interfaceName;
    /**
     * The name of the method to be called
     */
    private String methodName;
    /**
     * The name of the parameters to be called
     */
    private Object[] parameters;
    /**
     * The name of the parameter's type to be called
     */
    private Class<?>[] paramTypes;
}
