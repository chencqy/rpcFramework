package ncl.chen.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Qiuyu
 */

@Data
@AllArgsConstructor
public class ByeObject implements Serializable {
    private static final long serialVersionUID = 5262574609862286683L;

    private Integer id;
    private String message;
}
