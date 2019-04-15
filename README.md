# treasures
> 积跬步，以至千里。
## notes-basis
  * basic包中的内容是之前使用JDK的时候，由于对源码不够熟悉，遇到过的问题。
  * io包中的内容是用JDK原生io和Netty包，分别完成简单的阻塞和非阻塞的Http服务器。
  * proxy包中的内容是JDK动态代理的实现，简单模拟了一下Mybatis核心代理模式的实现。
  * reflection包中的是java反射相关的内容，包含JDK原生反射代码，和工具类Reflections和Apache的Commons工具包的使用。
  * rmi包的内容，简单的实现了RMI功能。
  * serializable包的内容是java序列化功能的实际运用。
  * thread包中的内容，包含JDK的thread包中各种并发工具类，线程池，Threadlocal等的使用。
  
## notes-middleware
 * zookeeper包中包含使用Zookeeper原生api和使用apache的curator的代码，包含了节点监控和分布式锁的实现。
 * redis包中包含了使用redis中各种数据结构的示例。Comments类里面把这些内容进行了汇总，包含了没有写出示范代码的一些方法。RedissonTest是使用Redisson框架完成分布式锁等功能。
 * mq包中的内容是java使用RabbitMq的示例，子包名对应各种模式的名称，包含直接绑定队列，direct，topics，rpc等各种模式。
  
## notes-framework
> 这个module中的代码，对应到个人笔记中 常用框架 模块儿的内容，会包含一些框架使用的Demo及其最佳实践等等（这个moudle后续可能会再度拆分）。  
> 框架源码的阅读和原理的剖析，陆续也会想办法迁移到GitHub中。  
> 这块准备做成一个大的融合部分，类似于SpringBoot集成各种常用框架和常用中间件的Demo。  

