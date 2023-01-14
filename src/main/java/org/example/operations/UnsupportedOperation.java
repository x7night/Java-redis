package org.example.operations;

import org.example.annotation.Command;
import org.example.compoment.RedisClient;
import org.example.enums.RedisObjectType;
import org.example.enums.ExecuteResult;
@Command("unsupported")
public class UnsupportedOperation extends Operation {
    private final String name = "unsupported operation";

    @Override
    public RedisObjectType getDataType() {
        return RedisObjectType.NONE;
    }

    @Override
    public boolean beforeExec(RedisClient client) {
        client.setOutput(ExecuteResult.UNSUPPORTED.getValue());
        return false;
    }

    @Override
    public boolean exec(RedisClient client) {
        return false;
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return false;
    }
}
