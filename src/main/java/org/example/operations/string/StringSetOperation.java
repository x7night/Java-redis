package org.example.operations.string;

import org.example.App;
import org.example.compoment.RedisClient;
import org.example.compoment.RedisServer;
import org.example.annotation.Command;
import org.example.compoment.RedisObject;
import org.example.datastruct.SDS;
import org.example.enums.DataType;
import org.example.enums.RedisObjectType;
import org.example.enums.ExecuteResult;

@Command("set")
public class StringSetOperation extends StringOperation {
    private final String name = "set";

    @Override
    public boolean beforeExec(RedisClient client) {
        if (client.getArgv().length != 3) {
            client.setOutput(ExecuteResult.FAIL.getValue());
            return false;
        }
        return true;
    }

    @Override
    public boolean exec(RedisClient client) {
        RedisObject<SDS> data = new RedisObject(RedisObjectType.STRING, DataType.SDS, new SDS(client.getArgv()[2]));
        client.getDb().put(client.getArgv()[1], data);
        client.setOutput(ExecuteResult.OK.getValue());
        return true;
    }

    @Override
    public boolean afterExec(RedisClient client) {
        RedisServer server = (RedisServer) App.CONTEXT.get("server");
        if(server.isSyncAOF()){
            int len = client.getArgv().length;
            if (len != 0) {
                server.getAofBuf().append("*").append(len).append("\r\n");
                for (int i = 1; i < len; i++) {
                    server.getAofBuf()
                            .append("$").append(client.getArgv()[i].length()).append("\r\n")
                            .append(client.getArgv()[i]).append("\r\n");

                }
            }
        }
        return true;
    }
}