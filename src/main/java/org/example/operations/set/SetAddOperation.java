package org.example.operations.set;

import org.example.annotation.Command;
import org.example.compoment.RedisClient;
import org.example.compoment.RedisObject;
import org.example.enums.DataType;
import org.example.enums.ExecuteResult;
import org.example.enums.RedisObjectType;
import org.example.operations.hash.HashOperation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Command("sadd")
public class SetAddOperation extends SetOperation {
    private String name = "hset";
    @Override
    public boolean beforeExec(RedisClient client) {
        if (client.getArgv().length < 3) {
            client.setOutput(ExecuteResult.FAIL.getValue());
            return false;
        }
        return true;
    }

    @Override
    public boolean exec(RedisClient client) {
        RedisObject ret = client.getDb().get(client.getArgv()[1]);

        RedisObject<Set> data;
        Set<String> set;
        if (RedisObject.NONE.equals(ret)) {
            set = new HashSet<>();
            data = new RedisObject<>(RedisObjectType.SET, DataType.HASH_SET, set);
        }else{
            set = ((Set) ret.getData());
            data = ret;
        }
        for (int i = 2; i < client.getArgv().length; i++) {
            set.add(client.getArgv()[i]);
        }
        client.getDb().put(client.getArgv()[1], data);
        client.setOutput(":1\r\n");
        return true;
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return true;
    }
}
