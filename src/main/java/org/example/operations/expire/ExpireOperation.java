package org.example.operations.expire;

import org.example.compoment.RedisClient;
import org.example.enums.RedisObjectType;
import org.example.operations.Operation;

public class ExpireOperation extends Operation {
    private static final String NAME = "expire";

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
        if(client.getDb().getData().containsKey(client.getArgv()[1])){
            long expireTime = Long.valueOf(client.getArgv()[2])*1000 + System.currentTimeMillis();
            client.getDb().getExpires().put(client.getArgv()[1], expireTime);
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
