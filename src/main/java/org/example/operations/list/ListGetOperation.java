package org.example.operations.list;

import org.example.compoment.RedisClient;
import org.example.compoment.RedisObject;
import org.example.enums.ExecuteResult;
import org.example.util.Encoder;

public class ListGetOperation extends ListOperation {
    private static final String NAME = "lget";

    @Override
    public boolean beforeExec(RedisClient client) {
        if(client.getArgv().length != 2){
            client.setOutput(ExecuteResult.FAIL.getValue());
            return false;
        }
        return true;
    }

    @Override
    public boolean exec(RedisClient client) {
        RedisObject data = client.getDb().getData()
                .getOrDefault(client.getArgv()[1], RedisObject.NONE);
        if(client.getDb().getExpires().containsKey(client.getArgv()[1])){
            data = System.currentTimeMillis() > client.getDb().getExpires().get(client.getArgv()[1])?
                    RedisObject.NONE:
                    data;
        }
        client.setOutput(Encoder.encodeData(data));
        return true;
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return true;
    }

    @Override
    public String getOperationName() {
        return NAME;
    }
}
