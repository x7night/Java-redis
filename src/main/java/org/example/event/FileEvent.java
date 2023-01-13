package org.example.event;

import lombok.Builder;
import lombok.Data;
import org.example.compoment.RedisClient;
import org.example.handler.ReplyHandler;
import org.example.handler.RequestHandler;

@Data
@Builder
public class FileEvent{

    /**
     * 事件类型
     */
    private int eventType;
    /**
     * 请求处理器
     */
    private RequestHandler requestHandler;
    /**
     * 回复处理器
     */
    private ReplyHandler replyHandler;
    /**
     * 客户端
     */
    private RedisClient client;
}
