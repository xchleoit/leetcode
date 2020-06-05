package algothriom.StategyModle.controller;

import algothriom.StategyModle.hanle.DealHandle;
import algothriom.StategyModle.hanle.DealHandleImpl1;
import algothriom.StategyModle.hanle.DealHandleImpl2;
import algothriom.StategyModle.hanle.HandleEnum;

import java.util.HashMap;
import java.util.Map;

public class Handle {
    private static Map<String, DealHandle> handleMap = new HashMap<>();
    static {
        handleMap.put(HandleEnum.HANDLE_ENUM1.value(),new DealHandleImpl1());
        handleMap.put(HandleEnum.HANDLE_ENUM2.value(),new DealHandleImpl2());
    }
    public static DealHandle instance (int type){
        return handleMap.get(HandleEnum.valueOf(type).value());
    }
}
