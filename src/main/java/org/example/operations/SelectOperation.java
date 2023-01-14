package org.example.operations;

import org.example.annotation.Command;
import org.example.enums.RedisObjectType;
import org.example.enums.ExecuteResult;
import org.example.compoment.RedisClient;
import org.example.compoment.RedisServer;
@Command("select")
public class SelectOperation extends Operation {
    private static final String NAME = "select";
    @Override
    public RedisObjectType getDataType() {
        return RedisObjectType.NONE;
    }

    @Override
    public String getOperationName() {
        return NAME;
    }

    @Override
    public boolean beforeExec(RedisClient client) {
        if (client.getArgv().length != 2) {
            client.setOutput(ExecuteResult.FAIL.getValue());
            return false;
        }
        return true;
    }

    @Override
    public boolean exec(RedisClient client) {
        client.setDb(RedisServer.dbs.get(Integer.parseInt(client.getArgv()[1])));
        client.setOutput(ExecuteResult.OK.getValue());
        return true;
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return true;
    }
}
