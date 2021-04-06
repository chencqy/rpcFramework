package ncl.chen.rpc.test;

import ncl.chen.rpc.annotation.Service;
import ncl.chen.rpc.api.HelloObject;
import ncl.chen.rpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Qiuyu
 */
@Service
public class HelloServiceImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("Received：{}", object.getMessage());
        return "Return value，id=" + object.getId();
    }

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }
}
