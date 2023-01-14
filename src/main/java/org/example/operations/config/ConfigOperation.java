package org.example.operations.config;

import org.example.annotation.Command;
import org.example.compoment.RedisClient;
import org.example.enums.RedisObjectType;
import org.example.operations.Operation;

import java.util.HashMap;
import java.util.Map;
@Command("config")
public class ConfigOperation extends Operation {
    private final String name = "config";

    final Map<String, Operation> configOperations = new HashMap<>();

    public ConfigOperation() {
        configOperations.put("get", new ConfigGetOperation());
    }

    @Override
    public RedisObjectType getDataType() {
        return null;
    }

    @Override
    public boolean beforeExec(RedisClient client) {
        return configOperations.get(client.getArgv()[1]).beforeExec(client);
    }

    @Override
    public boolean exec(RedisClient client) {
        return configOperations.get(client.getArgv()[1]).exec(client);
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return configOperations.get(client.getArgv()[1]).afterExec(client);
    }
}
