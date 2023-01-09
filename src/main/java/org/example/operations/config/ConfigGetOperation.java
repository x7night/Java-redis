package org.example.operations.config;

import org.example.compoment.RedisClient;
import org.example.compoment.RedisServer;
import org.example.enums.ExecuteResult;
import org.example.operations.client.ClientOperation;

public class ConfigGetOperation extends ClientOperation {
    private int argNum = 3;
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
