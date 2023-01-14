package org.example.operations.client;

import org.example.annotation.Command;
import org.example.compoment.RedisClient;
import org.example.enums.RedisObjectType;
import org.example.operations.Operation;

import java.util.HashMap;
import java.util.Map;
@Command("client")
public class ClientOperation extends Operation {
    private final String name = "client";

    private final Map<String, Operation> clientOperations = new HashMap<>();

    public ClientOperation() {
        clientOperations.put("setname", new ClientSetNameOperation());
    }


    @Override
    public RedisObjectType getDataType() {
        return null;
    }

    @Override
    public boolean beforeExec(RedisClient client) {
        return clientOperations.get(client.getArgv()[1]).beforeExec(client);
    }

    @Override
    public boolean exec(RedisClient client) {
        return clientOperations.get(client.getArgv()[1]).exec(client);
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return clientOperations.get(client.getArgv()[1]).afterExec(client);
    }
}
