# rpcFramework [![Build Status](https://travis-ci.com/chencqy/rpcFramework.svg?branch=main)](https://travis-ci.com/chencqy/rpcFramework)
A simple rpc framework
## Background
rpcFramework是一款基于 Netty/Socket+Kyro/Json+Nacos 实现的 RPC 框架。

## Feature
* 两种网络传输方式：Java Socket, Netty
* 序列化方式: Kryo, Json, Hessian, Protobuf
* 负载均衡算法: Random, Round-Robin
* 服务注册中心: Nacos
* 自定义传输协议
* 服务通过自定义注解自动注册
## Install
fork 项目到自己的仓库，然后克隆项目到自己的本地：git clone git@github.com:username/rpcFramework.git，使用 IDE 打开，等待项目初始化完成。
安装配置Nacos并启动，默认端口8848。

## Usage
**实现服务类并添加Service注解**

```java
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
```
**服务提供端**
```java
@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9000,
                CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();
    }
}
```
**服务调用端**

```java
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient(CommonSerializer.DEFAULT_SERIALIZER);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        ByeService byeService = proxy.getProxy(ByeService.class);
        String resHello = helloService.hello("Qiuyu");
        String resBye = byeService.bye("Qiuyu");
        System.out.println(resHello);
        System.out.println(resBye);
    }
}
```
先启动服务端，再启动调用端。
