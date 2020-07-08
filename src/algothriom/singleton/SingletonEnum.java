package algothriom.singleton;/*
 * @author xulei
 * @date 2020-06-30 09:32
 * 概要：
 *     XXXXX
 *
 */

import java.sql.Connection;

public enum  SingletonEnum {
    connectionFactory;
    /*要实例化的类*/
    private Test connection;

    private SingletonEnum() {
        connection = new Test();
    }

    public Test newInstance(){
        return connection;
    }
}
