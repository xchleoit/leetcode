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

# 3 .多线程

	## 1.  使用无界队列的线程池会导致内存飙升吗？

* FixedThreadPool是一个无界队列的线程池,LinkedBlockQueue的最大任务数量为Integer.MAX_VALUE</br>

* 所以。<font color=red>如果WorkQueue里不断的积压越来越多的任务，不停的增加。这个过程会导致机器的内存使用飙升，最后也许极端的情况下导致JVM OOM，系统挂掉了</font>

  

