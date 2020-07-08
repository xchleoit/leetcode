package test.AOP.Cglib;/*
 * @author xulei
 * @date 2020-06-24 16:18
 * 概要：
 *     XXXXX
 *
 */

public class test {
    public static void main(String[] args){
        ServiceImpl service = new ServiceImpl();
        CGLIBPROXY cglibproxy = new CGLIBPROXY();
        ServiceImpl proxy = (ServiceImpl)cglibproxy.newInstance(service);
        proxy.add();
    }
}
