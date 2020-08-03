# 1. HashMap

## 1 . 有关HashMap基本知识

HashMap是一种基于数组链表的结合类，以key-value的形式表达。线程不安全。表征为一个数组，每个数据为一个Entry，每一个Entry为一个链表的形式存储数据。在java8后，如果链表过长会影响到数据读取，所以，如果链表长度大于8时，则该链表进化为红黑树的形式。如果红黑树节点个数小于6的时候，则退化为链表的形式。

相对的，HashMap的对应的线程安全的类为concuentHashMap。

他是对每个Entry进行加锁。保证线程安全。如果你修改的同一个ConcurentHashMap的其他Entry。理论上应该不会报错。

有序的Map，TreeMap，LinkedHashMap

##1.2 红黑树

* 红黑树的特点
  * 每个节点不是红的就是黑的
  * 根结点为黑色
  * 每个叶子结点为黑色(NIL)
  * 每个红色结点的子节点为黑色
  * 任一节点到每个叶子结点的路径包含相同的黑色结点
* 因为红黑树保证了 黑色完美平衡，所以他的查找最坏时间复杂度为O(2lgN)
* 插入操作
  * 左旋
  * 右旋
  * 变色

## 1.3 其他问题

 * 1. HashMap的长度为2^n-1?

      为了对Map的key的hash值进行一一比对

* 

# 2 . 集合

>List线程安全的实现方式

```java
        List list1 = new Vector();//sychronized实现
				//synchronized(metux)实现
        List list2 =  Collections.synchronizedList(new ArrayList<>());
				//ReentrantLock实现
        List list3 =  new CopyOnWriteArrayList();
```

> set线程安全的实现方式

```java
        Set<String> set2 = Collections.synchronizedSet(new HashSet<>());
        Set<String> set1 = new CopyOnWriteArraySet<>();
```



# 3 .多线程

	## 1.  使用无界队列的线程池会导致内存飙升吗？

* FixedThreadPool是一个无界队列的线程池,LinkedBlockQueue的最大任务数量为Integer.MAX_VALUE</br>

* 所以。<font color=red>如果WorkQueue里不断的积压越来越多的任务，不停的增加。这个过程会导致机器的内存使用飙升，最后也许极端的情况下导致JVM OOM，系统挂掉了</font>

  

# 4. 四大函数式计算

### 1. lambda表达式



### 2. 链式编程



### 3. 函数式接口

>**函数式接口**：只有一个方法的接口

```java
//@FunctionalInterface简化编程模型
//在新版本的底层框架中大量应用
@FunctionalInterface
public interface Runnable {
    public abstract void run();
}
```

> 四大基本函数式接口：Consumer, Function,Predicate,Supplier

1. 函数式接口（Function.apply(T,R)）

   * 有一个输入，有一个输出

   * 只要是函数式接口，就可以用lambda表达式简化

2. 断定型接口（Predicate.test(T)）
   * 有一个输入，返回值固定为boolean类型

3. 消费型接口（Consumer.accept(T）
   * 只有输入，没有返回值

4. 供给型接口（Supplier.get()）
   	*  没有输入，只有返回值

### 4. Steam流式计算

```java
public class ReviewJavaTool {
    public static void main(String[] args) throws Exception {
        User u1= new User("A",6);
        User u2= new User("B",2);
        User u3= new User("C",3);
        User u4= new User("D",4);
        User u5= new User("E",5);
        /**
         * 要求输出的
         * 年龄必须为偶数
         * 年龄大于3岁
         * 用户名转小写
         * 倒着排序
         * 只输出一个用户
         * */
        List<User> list = Arrays.asList(u1,u2,u3,u4,u5);
        list.stream().filter((u)->{return u.getAge()%2==0;})
                .filter((u)->{return u.getAge()>3;})
                .map((user)->{
                     user.setNo(user.getNo().toLowerCase());
                     return user;
                }).sorted((User o1,User o2)->{
          					 return o2.getNo().compareTo(o1.getNo());
                }).limit(1)
                .forEach((u)->{
            System.out.println(u.toString());
        });
    }
}
class User {
    private String no;
    private int age;
    @Override
    public String toString() {
        return "User{" +
                "no='" + no + '\'' +
                ", age=" + age +
                '}';
    }
    public User(String no, int age) {
        this.no = no;
        this.age = age;
    }
    public String getNo() {
        return no;
    }
    public void setNo(String no) {
        this.no = no;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
```



# 5. 锁

## 5.1 死锁的排查

> 死锁的例子

```java
public class ReviewJavaTool {
    public static void main(String[] args) throws Exception {        
				String lockA=  "lockA";
        String lockB=  "lockB";
        new Thread(new MyThread(lockA,lockB),"A").start();
        new Thread(new MyThread(lockB,lockA),"B").start();

    }
}


class MyThread implements Runnable{
    private String lockA;
    private String lockB;

    public MyThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println("现在是线程："+Thread.currentThread().getName()+"获得的锁："+lockA+"正想获取锁："+lockB);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println("现在是线程："+Thread.currentThread().getName()+"获得的锁："+lockB+"正想获取锁："+lockA);
            }
        }
    }
}
```



控制台显示

```java
现在是线程：A获得的锁：lockA正想获取锁：lockB
现在是线程：B获得的锁：lockB正想获取锁：lockA
```

```java
//~/Desktop/javaProject/leetcode(master*) » jps -l     通过此命令来获取pid列表                       
admin@localhost
11681 test.ReviewJavaTool
11682 org.jetbrains.jps.cmdline.Launcher
8132 org.jetbrains.kotlin.daemon.KotlinCompileDaemon
12089 sun.tools.jps.Jps
623 

```



```java
//通过jstack 11681(pid) 来获取
Found one Java-level deadlock:
=============================
"B":
  waiting to lock monitor 0x00007f849901dc18 (object 0x000000076aeed970, a java.lang.String),
  which is held by "A"
"A":
  waiting to lock monitor 0x00007f8499020298 (object 0x000000076aeed9a8, a java.lang.String),
  which is held by "B"

Java stack information for the threads listed above:
===================================================
"B":
        at test.MyThread.run(ReviewJavaTool.java:158)
        - waiting to lock <0x000000076aeed970> (a java.lang.String)
        - locked <0x000000076aeed9a8> (a java.lang.String)
        at java.lang.Thread.run(Thread.java:748)
"A":
        at test.MyThread.run(ReviewJavaTool.java:158)
        - waiting to lock <0x000000076aeed9a8> (a java.lang.String)
        - locked <0x000000076aeed970> (a java.lang.String)
        at java.lang.Thread.run(Thread.java:748)

Found 1 deadlock.

```



$$R_p= \cfrac {等待时间+要求服务时间}{要求服务时间}=\cfrac{响应时间}{要求服务时间}$$



