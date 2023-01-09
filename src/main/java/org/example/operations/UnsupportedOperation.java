package org.example.operations;

import org.example.compoment.RedisClient;
import org.example.enums.RedisObjectType;
import org.example.enums.ExecuteResult;

public class UnsupportedOperation extends Operation {
    private static final String NAME = "unsupported operation";

    @Override
    public RedisObjectType getDataType() {
        return RedisObjectType.NONE;
    }

    @Override
    public String getOperationName() {
        return NAME;
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
