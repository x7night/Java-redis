package org.example.operations.config;

import org.example.compoment.RedisClient;
import org.example.compoment.RedisServer;
import org.example.enums.RedisObjectType;
import org.example.operations.Operation;

public class ConfigGetOperation extends Operation {
    private int argNum = 3;

    @Override
    public RedisObjectType getDataType() {
        return null;
    }

    @Override
    public boolean beforeExec(RedisClient client) {
        return argNum == client.getArgv().length;
    }

    @Override
    public boolean exec(RedisClient client) {
        StringBuffer res = new StringBuffer();
        if("databases".equals(client.getArgv()[2])){
            String numStr = String.valueOf(RedisServer.dbs.size());
            res.append("*2\r\n$9\r\ndatabases\r\n$").append(numStr.length()).append("\r\n").append(numStr).append("\r\n");
        }
        client.setOutput(res.toString());
        return true;
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return true;
    }

}
