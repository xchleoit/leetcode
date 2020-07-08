package algothriom.singleton;/*
 * @author xulei
 * @date 2020-06-30 09:26
 * 概要：
 *     静态内部类实现
 *
 */

public class SingletonStaticInner {

//    内部类

    private static class InnerClass{
        private static SingletonStaticInner singletonStaticInner =  new SingletonStaticInner();
    }

    private SingletonStaticInner() {
    }

    public static SingletonStaticInner newInstance(){
        return InnerClass.singletonStaticInner;
    }
}
