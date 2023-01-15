package org.example.operations.hash;

import org.example.annotation.Command;
import org.example.compoment.RedisClient;
import org.example.compoment.RedisObject;
import org.example.enums.DataType;
import org.example.enums.ExecuteResult;
import org.example.enums.RedisObjectType;

import java.util.HashMap;
import java.util.Map;
@Command("hset")
public class HashSetOperation extends HashOperation{
    private final String name = "hset";
    @Override
    public boolean beforeExec(RedisClient client) {
        if (client.getArgv().length < 4 || client.getArgv().length % 2 != 0) {
            client.setOutput(ExecuteResult.FAIL.getValue());
            return false;
        }
        return true;
    }

    @Override
    public boolean exec(RedisClient client) {
        Map<String, String> map = new HashMap<>();
        for (int i = 2; i < client.getArgv().length; i += 2) {
            map.put(client.getArgv()[i], client.getArgv()[i+1]);
        }
        RedisObject<Map<String, String>> data = new RedisObject<>(RedisObjectType.HASH, DataType.HASH_MAP, map);
        client.getDb().put(client.getArgv()[1], data);
        return true;
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return true;
    }
}
