package ncl.chen.rpc.test;

import ncl.chen.rpc.annotation.Service;
import ncl.chen.rpc.api.ByeObject;
import ncl.chen.rpc.api.ByeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Qiuyu
 */
@Service
public class ByeServiceImpl implements ByeService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String bye(ByeObject object) {
        logger.info("Received：{}", object.getMessage());
        return "Return value，id=" + object.getId();
    }

    @Override
    public String bye(String name) {
        return "Bye " + name;
    }
}
