package org.example.operations;

import org.example.annotation.Command;
import org.example.compoment.RedisClient;
import org.example.enums.RedisObjectType;
@Command("del")
public class DeleteOperation extends Operation{
    private final String name = "del";
    @Override
    public RedisObjectType getDataType() {
        return null;
    }

    @Override
    public boolean beforeExec(RedisClient client) {
        return true;
    }

    @Override
    public boolean exec(RedisClient client) {
        client.getDb().remove(client.getArgv()[1]);
        client.setOutput(":1\r\n");
        return true;
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return true;
    }
}
