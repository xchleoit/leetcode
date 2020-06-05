package algothriom.StategyModle.controller;

import algothriom.StategyModle.hanle.DealHandle;

/**
 * author：xchleoit
 * Date:2020.06.04
 * 解释：
 * 算法：策略模式
 * DealHandle:处理类的接口，
 * DealHandleImpl1:继承了DealHandle的具体处理类1
 * DealHandleImpl1:继承了DealHandle的具体处理类1
 * HandEnum:枚举关键字
 * Handle:将枚举关键字与具体处理类映射
 * */
public class HandleMain {

    public static void main(String[] args) {
        DealHandle handle = Handle.instance(1);
        handle.handle();
    }
}
