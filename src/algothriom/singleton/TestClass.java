package algothriom.singleton;/*
 * @author xulei
 * @date 2020-06-30 09:44
 * 概要：
 *     测试类
 *
 */

public class TestClass {
    public static void main(String[] args){
        SingletonDL singletonDL = SingletonDL.newInstance();
        SingletonStaticInner singletonStaticInner = SingletonStaticInner.newInstance();
        Test test = SingletonEnum.connectionFactory.newInstance();
    }
}
