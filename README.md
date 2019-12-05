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
 * redis包中包含了使用redis中各种数据结构的示例。Comments类里面把这些内容进行了汇总，包含了没有写出示范代码的一些方法。RedissonTest是使用Redisson框架完成分布式锁等功能。ClusterClient是使用redis集群的客户端示例.包含了服务器集群和客户分片的两种情况.
 * mq包中的内容是java使用RabbitMq的示例，子包名对应各种模式的名称，包含直接绑定队列，跟路由相关的direct，topics，rpc等各种模式。
 * elasticsearch包中的内容是使用transportClient与ES交互的一些常用API,包含term查询,全文检索,父子文档查询,聚合查询等.
  
## notes-framework
  * 使用SpringBoot构建简单的Web应用。
  * 整合Mybatis与Mysql数据库进行简单的交互。
  * 整合Jdbc-Sharding框架进行分库分表和读写分离。
  * 整合PageHelper完成分页功能。
  * 使用Thymeleaf完成前端渲染。
  * 使用Redis缓存数据减少数据库压力，结合Ehcache实现预热，降级等策略预防缓存雪崩，穿透等问题。
  * 使用ElasticSearch做搜索,完成统一的异常处理和统一的错误页面,配置statics下为静态资源,templates下为模版。
  * 使用RabbitMq做异步推送,订单在当前系统生成,详情推送到Erp系统。
  * ...
  > @TODO 结合Nginx，RabbitMq，Zookeeper，Dubbo，ElasticSearch，完成分布式的高可用架构。


