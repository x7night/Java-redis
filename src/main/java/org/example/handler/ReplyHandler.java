package org.example.handler;

import org.example.compoment.RedisClient;

/**
 * 命令回复处理器
 */
public class ReplyHandler {
    private static ReplyHandler instance = new ReplyHandler();
    public static ReplyHandler getInstance(){
        return instance;
    }

    public void handel(RedisClient client){

    }
}
