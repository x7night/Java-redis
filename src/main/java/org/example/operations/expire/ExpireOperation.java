package org.example.operations.expire;

import org.example.annotation.Command;
import org.example.compoment.RedisClient;
import org.example.compoment.RedisObject;
import org.example.enums.RedisObjectType;
import org.example.operations.Operation;
@Command("expire")
public class ExpireOperation extends Operation {
    private final String name = "expire";

    @Override
    public RedisObjectType getDataType() {
        return null;
    }

    @Override
    public boolean beforeExec(RedisClient client) {
        return true;
    }

    @Override
    public boolean exec(RedisClient client) {
        RedisObject key = client.getDb().getKey(client.getArgv()[1]);
        if(null != key){
            long expireTime = Long.valueOf(client.getArgv()[2])*1000 + System.currentTimeMillis();
            client.getDb().getExpires().put(key, expireTime);
            client.setOutput(":1\r\n");
        }else{
            client.setOutput(":0\r\n");
        }
        return true;
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return false;
    }
}
