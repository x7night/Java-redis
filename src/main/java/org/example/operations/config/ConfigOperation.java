package org.example.operations.config;

import org.example.annotation.Command;
import org.example.compoment.RedisClient;
import org.example.enums.RedisObjectType;
import org.example.operations.Operation;

import java.util.HashMap;
import java.util.Map;
@Command("config")
public class ConfigOperation extends Operation {
    private static final String NAME = "config";

    static final Map<String, Operation> CLIENT_OPERATIONS = new HashMap<>();
    static {
        CLIENT_OPERATIONS.put("get", new ConfigGetOperation());
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
