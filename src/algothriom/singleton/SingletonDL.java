package algothriom.singleton;/*
 * @author xulei
 * @date 2020-06-30 09:19
 * 概要：
 *     双重锁机制
 *
 */

public class SingletonDL {
    private static volatile SingletonDL singletonDL;

    private SingletonDL() {
    }

    public static SingletonDL newInstance() {

        if (singletonDL == null){
            synchronized (SingletonDL.class){
                if (singletonDL == null){
                    singletonDL =  new SingletonDL();
                }
            }
        }
        return singletonDL;
    }
}
