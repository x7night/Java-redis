package org.example.operations;

import org.example.compoment.RedisClient;
import org.example.enums.RedisObjectType;

public class PingOperation extends Operation{
    private final String NAME = "ping";
    @Override
    public RedisObjectType getDataType() {
        return RedisObjectType.NONE;
    }

    @Override
    public boolean beforeExec(RedisClient client) {
        return client.getArgv().length == 1;
    }

    @Override
    public boolean exec(RedisClient client) {
        client.setOutput("+PONG\r\n");
        return true;
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return true;
    }
}
