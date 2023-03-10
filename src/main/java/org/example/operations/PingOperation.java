package org.example.operations;

import org.example.annotation.Command;
import org.example.compoment.RedisClient;
import org.example.enums.RedisObjectType;
@Command("ping")
public class PingOperation extends Operation{
    private String name = "ping";
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
