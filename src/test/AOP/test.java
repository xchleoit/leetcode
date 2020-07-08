package test.AOP;/*
 * @author xulei
 * @date 2020-06-24 16:00
 * 概要：
 *     XXXXX
 *
 */

public class test {
    public static void main(String[] args) {
        TestService testService = new TestServiceImpl();
        JDKAOP jdkaop = new JDKAOP();
        TestService proxy = (TestService)jdkaop.newProxy(testService);
        proxy.add();
    }
}
