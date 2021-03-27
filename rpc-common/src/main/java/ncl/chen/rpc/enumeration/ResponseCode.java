package ncl.chen.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: Qiuyu
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS(200,"Method call succeeded"),
    FAIL(500,"Method call failed"),
    METHOD_NOT_FOUND(500,"The method was not found"),
    CLASS_NOT_FOUND(500,"The class was not found");

    private final int code;
    private final String message;

}
