package org.example.operations.client;

import org.example.compoment.RedisClient;
import org.example.enums.ExecuteResult;
import org.example.enums.RedisObjectType;
import org.example.operations.Operation;

public class ClientSetNameOperation extends Operation {
    private int argNum = 3;

    @Override
    public RedisObjectType getDataType() {
        return null;
    }

    @Override
    public boolean beforeExec(RedisClient client) {
        return argNum == client.getArgv().length;
    }

    @Override
    public boolean exec(RedisClient client) {
        client.setName(client.getArgv()[2]);
        client.setOutput(ExecuteResult.OK.getValue());
        return true;
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return true;
    }

}
