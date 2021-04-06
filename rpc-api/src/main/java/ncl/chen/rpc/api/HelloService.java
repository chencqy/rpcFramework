package ncl.chen.rpc.api;

/**
 * @author: Qiuyu
 */
public interface HelloService {
    String hello(HelloObject object);
    String hello(String name);
}
