package test.AOP;/*
 * @author xulei
 * @date 2020-06-24 15:52
 * 概要：
 *     XXXXX
 *
 */

public class TestServiceImpl implements TestService{
    @Override
    public void add() {
        System.out.println("add 方法");
    }
}
