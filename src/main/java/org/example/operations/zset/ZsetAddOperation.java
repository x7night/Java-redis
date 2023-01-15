package org.example.operations.zset;

import org.example.annotation.Command;
import org.example.compoment.RedisClient;
import org.example.compoment.RedisObject;
import org.example.datastruct.Zset;
import org.example.enums.DataType;
import org.example.enums.RedisObjectType;

@Command("zadd")
public class ZsetAddOperation extends ZsetOperation{
    private final String name = "zadd";
    @Override
    public boolean beforeExec(RedisClient client) {
        return true;
    }

    @Override
    public boolean exec(RedisClient client) {
        RedisObject ret = client.getDb().get(client.getArgv()[1]);

        RedisObject<Zset> data;
        Zset set;
        if (RedisObject.NONE.equals(ret)) {
            set = new Zset();
            data = new RedisObject<>(RedisObjectType.ZSET, DataType.SKIP_LIST, set);
        }else{
            set = (Zset) ret.getData();
            data = ret;
        }
        for (int i = 2; i < client.getArgv().length; i+=2) {
            set.add(client.getArgv()[i], client.getArgv()[i+1]);
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
