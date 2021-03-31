package ncl.chen.rpc.api;

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
public class HelloObject implements Serializable {
    private static final long serialVersionUID = 2009001169580825284L;
    private Integer id;
    private String message;
}
