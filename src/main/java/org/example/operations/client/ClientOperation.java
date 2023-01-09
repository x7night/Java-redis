package org.example.operations.client;

import org.example.compoment.RedisClient;
import org.example.enums.RedisObjectType;
import org.example.operations.Operation;

import java.util.HashMap;
import java.util.Map;

public class ClientOperation extends Operation {
    private static final String NAME = "client";

    static final Map<String, Operation> CLIENT_OPERATIONS = new HashMap<>();
    static {
        CLIENT_OPERATIONS.put("setname", new ClientSetNameOperation());
    }


    @Override
    public RedisObjectType getDataType() {
        return null;
    }

    @Override
    public String getOperationName() {
        return NAME;
    }

    @Override
    public boolean beforeExec(RedisClient client) {
        return CLIENT_OPERATIONS.get(client.getArgv()[1]).beforeExec(client);
    }

    @Override
    public boolean exec(RedisClient client) {
        return CLIENT_OPERATIONS.get(client.getArgv()[1]).exec(client);
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return CLIENT_OPERATIONS.get(client.getArgv()[1]).afterExec(client);
    }
}
