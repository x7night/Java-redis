package org.example.operations.client;

import org.example.compoment.RedisClient;
import org.example.compoment.RedisServer;
import org.example.enums.ExecuteResult;

public class ClientSetNameOperation extends ClientOperation {
    private int argNum = 3;
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
