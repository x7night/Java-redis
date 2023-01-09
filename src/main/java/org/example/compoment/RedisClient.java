package org.example.compoment;

import lombok.Data;
import org.example.datastruct.Database;
import org.example.command.RedisCommand;
import org.example.datastruct.SDS;

import java.nio.channels.SocketChannel;

@Data
public class RedisClient {
    /**
     * 客户端名称
     */
    private String name;
    /**
     * 客户端标识
     */
    private Integer flags;
    /**
     * 输入缓冲区
     */
    private SDS queryBuffer;
    /**
     * 输出缓冲区
     */
    private String output;
    /**
     * 命令参数
     */
    private String[] argv;
    /**
     * 命令
     */
    private RedisCommand cmd;
    /**
     * 数据库
     */
    private Database db;
    /**
     * 身份验证信息
     */
    private Integer authenticated;
    /**
     * 客户端创建时间
     */
    private Long createdTime;
    /**
     * 最后交互时间
     */
    private Long lastInteraction;
    /**
     * socket连接
     */
    private SocketChannel socketChannel;

    public RedisClient(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }



    public void exec(){
        this.cmd.execCommand(this);
        lastInteraction = System.currentTimeMillis();
    }
}
