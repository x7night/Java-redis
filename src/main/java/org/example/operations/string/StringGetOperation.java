package org.example.operations.string;

import org.example.compoment.RedisClient;
import org.example.annotation.Command;
import org.example.compoment.RedisObject;
import org.example.enums.ExecuteResult;
import org.example.util.Encoder;

@Command("get")
public class StringGetOperation extends StringOperation {
    private final static String NAME = "get";

    @Override
    public String getOperationName() {
        return NAME;
    }

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
        RedisObject res = client.getDb()
                .get(client.getArgv()[1]);
        client.setOutput(Encoder.encodeData(res));
        return true;
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return true;
    }

}
