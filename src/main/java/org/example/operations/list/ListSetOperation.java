package org.example.operations.list;

import org.example.annotation.Command;
import org.example.compoment.RedisClient;
import org.example.compoment.RedisObject;
import org.example.enums.DataType;
import org.example.enums.RedisObjectType;
import org.example.enums.ExecuteResult;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
@Command("lset")
public class ListSetOperation extends ListOperation {
    private String NAME = "lset";

    @Override
    public boolean beforeExec(RedisClient client) {
        if(client.getArgv().length < 3){
            client.setOutput(ExecuteResult.FAIL.getValue());
            return false;
        }
        return true;
    }

    @Override
    public boolean exec(RedisClient client) {
        RedisObject<List> data = new RedisObject(RedisObjectType.LIST,
                DataType.LINKED_LIST,
                new LinkedList<>(List.of(Arrays.copyOfRange(client.getArgv(), 2, client.getArgv().length))));
        client.getDb()
                .put(client.getArgv()[1], data);
        client.setOutput(ExecuteResult.OK.getValue());
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
