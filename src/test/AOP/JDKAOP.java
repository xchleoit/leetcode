package test.AOP;/*
 * @author xulei
 * @date 2020-06-24 15:53
 * 概要：
 *     XXXXX
 *
 */


import java.lang.reflect.*;



public class JDKAOP implements InvocationHandler {
    private Object proObject;

    public Object newProxy(Object proObject){
        this.proObject =  proObject;
        return Proxy.newProxyInstance(proObject.getClass().getClassLoader(),proObject.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object object =  method.invoke(this.proObject ,args);
        after();
        return object;
    }
    public void before(){
        System.out.println("开始执行目标对象之前...");
     }

    public void after() {
        System.out.println("开始执行目标对象之后...");
    }
}
