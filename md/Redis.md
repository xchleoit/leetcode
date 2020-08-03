# 1. Redis基础

## 1.2 Redis 6.0的13 问

### 1.2.1  Redis6.0之前都版本真的是单线程的？

> 答：Redis处理客户端的请求时，包括获取（socket读）、解析、执行、内容返回（socket写）等都是由一个串行化。但是Redis4.0后，除了任务处理等线程外，还有一些，辅助的线程（清理垃圾数据，释放连接，删除key等）是并发的【为了处理一些较为缓慢的操作】

### 1.2.2 Redis6.0之前的版本为什么不使用多线程

> 答：单线程可维护性高。多线程虽然表现优异，但是却引入了程序执行的不稳定性，同时，可能存在线程切换，加/释放锁，死锁造成的性能损耗，且Redis通过AE事件模型以及IO多路复用等技术，处理性能非常高，没必要使用多线程。

### 1.2.3 Redis6.0为什么要引入多线程

> 答：

* 现在的Redis基本以及能满足大部分需求 
* 针对复杂业务场景，上亿的交易量，常用的解决是分布式进行数据分区，此方法缺点是维护复杂，数据分区后无法解决热点读/写问题。
* 目前redis自身上看，现在瓶颈在网络读写上，所以有两个解决：
  * 提供网络IO性能，替换为DPDK协议栈（就Redis本身而言，实现不实际）
  * 充分利用服务器CPU资源，【多线程任务可以分摊Redis同步IO读写负荷，<font color=red>这种方式可行，也是Redis实现多线程的理由</font>】

### 1.2.4 Redis 6.0 默认开启多线程？

> 答：默认禁用，如果要开启，修改redis.conf配置文件

```
io-threads-do-reads yes
```

### 1.2.5 Redis 6.0开启多线程时。如何设置线程数

> 答：开启多闲程后,需要设置线程数，负责不生效，同样修改redis.conf配置文件。

```
io-threads 4
```

> 官方建议：<font color=green>4核的机器建议设置为2-3个线程，8核的建议设置为6个线程，线程数一定要小于机器核数</font>。**还需要注意**:<font color=red>线程数不是越大越好，官方认为超过8个就没有意义了（没找到原因）</font>

### 1.2.6 是否要开启多线程

> 答：如果开启多线程，至少要4核的机器，且Redis实例已经占用相当大的CPU耗时的时候建议采用，否则多线程的意义不明显。所以估计80%的公司开发人员用不到多线程。

###  1.2.7 Redis6.0多线程的实现机制

>  答：实现机制

![实现机制](http://p6-tt.byteimg.com/large/pgc-image/2516a9351d0540099cada79ec4ce4d29?from=pc)

1. **<u>*流程如下*</u>**<font color=green>绿色代表多线程操作，之时读取并会写socket</font>

   1. 主线程（单线程）负责接收建立连接请求，获取socket放入全局等待处理队列
   2. 主线程处理完读事件后，通过RR(Round Robin)将这些连接分配给IO线程
   3. 主线程<font color=red>阻塞等待</font><font color=green>IO线程读取socket完毕</font>
   4. 主线程通过单线程的方式执行请求命令、请求数据并解析完成
   5. 主线程<font color=red>阻塞等待</font><font color=green>IO线程将数据会写socket完毕</font>
   6. 解除绑定，清空等待队列

2. 流程图

   1. 多线程的特点
      1. IO线程要么同时在读，要么同时在写，不会同时读或者写【读写时，主线程阻塞】
      2. IO线程只负责读写socket解析命令，不负责命令处理

   ![](http://p3-tt.byteimg.com/large/pgc-image/7e5208aaea334bf0ac5abf52b7ac644e?from=pc)

	### 1.2.8 开启多线程后，是否会存在线程并发安全问题

> 答：由上面的机制可以看出，redis多线程只是用来处理网络数据的读写和协议解析，执行命令仍然是单线程执行。所以不需要去考虑控制key，lua，事务，Lpush/Lpop等等的并发及安全问题。

### 1.2.9 Redis6.0 的多线程和Memcached多线程模型对比

> 答：
>
> * 相同点：都采用了master-worker线程模型
> * 不同点：
> * * Memcached执行主逻辑也是在worker线程里。模型更加简单，实现了真正的线程隔离。       
>   * Redis把处理逻辑交还给了master线程，虽然一定成都上增加了模型复杂度，但是也解决了并发安全问题

  ### 1.2.10 IO多路复用

多线程ReactorIO多路复用

![](https://upload-images.jianshu.io/upload_images/10041749-00efa4cb8729f0f0.png?imageMogr2/auto-orient/strip|imageView2/2/w/632)                                                  

* 流程：
  * Main Reactor负责监听外部请求连接，并派发给Acceptor处理
  * Acceptor接收连线后，会给client绑定一个Handler并注册到Sub Reactor上监听，对于有多个Sub Reactor的情况下，IO事件选择注册哪个Sub Reactor则是采用Round-robin的机制来分配
  * Sub Reactor负责监听事件，并派发IO事件给handler处理，sub Reactor线程的数量可以设置为CPU核心数



# 2 . Redis的常见问题

![缓存处理流程](https://img-blog.csdn.net/20180919143214712?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2tvbmd0aWFvNQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

##  2.1. Redis雪崩

> 答：redis设置了过期时间，很多数据的过期时间相同，同一时间过期，导致，过期后大范围的请求直接打到数据库，导致数据库IO压力过大。
>
> 解决：
>
> * 缓存数据的过期时间限制（加扰动值），防止同一时间大量数据过期
> * 如果换成数据是分布式部署，将热点数据均匀分布在不同数据库中（不会因为一个数据库失效，导致大范围失效）
> * 设置热点数据永不过期

## 2.2. Redis击穿

> 答：Redis里面没有此数据，数据库里面有此数据，导致大范围的请求，导致数据库IO压力过大
>
> 解决：
>
> * 设置热点永不过期
>
> * 加互斥锁（如果要请求数据库，加锁，请求到了立即生产缓存，让其他相同请求不在访问数据库）
>
>   ```java
>   public static String getData(String key) throws InterruptedException{
>   		//从缓存读取数据
>     String result = getDataFromRedis(key);
>     //如果缓存没有此数据
>     if(result == null){
>       	//获取锁，获取成功，再去访问数据库
>       	if (reenLock.tryLock()){
>           	//从数据库中获取数据
>             result = getDataFromSQL(key);
>             //更新redis数据
>           	if (result != null){
>               setDataToRedis(result);
>             }
>             //释放锁
>             reenLock.unlock();
>         }else{
>           //获取锁失败，稍等，再次获取
>           Thread.sleep(100);
>           result = getData(key);
>         }
>     }
>     return result;
>   }
>   ```

## 2.3 . Redis穿透

> 答： 指用户访问缓存和数据库中都没有的数据，这类访问，会急剧增加数据库IO压力，因为请求一直在出现。
>
> 结局：
>
> * 从接口层增加校验，如鉴权校验，Id基础校验
> * 从缓存取不到的数据，也生成key，不过value为null，让压力，不在积聚到数据库侧。
> * 布隆过滤器(可以用来告诉你，某样东西一定不存在或者可能不存在)

## 2.4 . 脑裂问题

​		由于网络问题，有一部分的slave节点失去联系，不能连上master节点；所以从新选举出一个master，现在就有两个master节点。导致数据不一致。

解决：

```
redis中有两个配置参数：

　　　　（旧版本）

　　　　　　min-slaves-to-write 3

　　　　　　min-slaves-max-lag 10

　　　　（新版本）

　　　　　　min-replicas-to-write 3

　　　　　　min-replicas-max-lag 10

　　第一个参数表示最少的salve节点为3个，第二个参数表示数据复制和同步的延迟不能超过10秒

　　　　配置了这两个参数：如果发生脑裂：原master会在客户端写入操作的时候拒绝请求。这样可以避免大量数据丢失。


```



# 3 . Redis为何快

#4 . 



