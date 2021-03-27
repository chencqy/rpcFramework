package ncl.chen.rpc.test;

import ncl.chen.rpc.api.ByeObject;
import ncl.chen.rpc.api.ByeService;
import ncl.chen.rpc.api.HelloObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Qiuyu
 */
public class ByeServiceImpl implements ByeService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String bye(ByeObject object) {
        logger.info("Received：{}", object.getMessage());
        return "Return value，id=" + object.getId();
    }
}
