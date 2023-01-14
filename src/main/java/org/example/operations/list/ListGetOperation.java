package org.example.operations.list;

import org.example.annotation.Command;
import org.example.compoment.RedisClient;
import org.example.compoment.RedisObject;
import org.example.enums.ExecuteResult;
import org.example.util.Encoder;

@Command("lget")
public class ListGetOperation extends ListOperation {
    private final String name = "lget";

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
        RedisObject data =  client.getDb()
                .get(client.getArgv()[1]);
        client.setOutput(Encoder.encodeData(data));
        return true;
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return true;
    }

}
