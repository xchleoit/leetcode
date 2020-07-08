package test.AOP.Cglib;/*
 * @author xulei
 * @date 2020-06-24 16:06
 * 概要：
 *     XXXXX
 *
 */
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
//import org.objectweb
//        .asm.ClassWriter;
import java.lang.reflect.Method;

public class CGLIBPROXY implements MethodInterceptor {
    private Object target;

    public Object newInstance(Object target){
        this.target = target;
        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;

        try {
            before();
            result =  methodProxy.invoke(target,objects);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            after();
        }
        return result;
    }

    public void before(){
        System.out.println("开始执行目标对象之前...");
    }

    public void after() {
        System.out.println("开始执行目标对象之后...");
    }
}
