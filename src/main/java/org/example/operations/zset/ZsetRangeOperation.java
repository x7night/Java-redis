package org.example.operations.zset;

import cn.hutool.core.collection.ListUtil;
import org.example.annotation.Command;
import org.example.compoment.RedisClient;
import org.example.compoment.RedisObject;
import org.example.datastruct.Zset;
import org.example.enums.DataType;
import org.example.util.Encoder;

import java.util.Arrays;
import java.util.List;
import java.util.NavigableMap;

@Command("zrange")
public class ZsetRangeOperation extends ZsetOperation{
    private final String name = "zrange";
    @Override
    public boolean beforeExec(RedisClient client) {
        return true;
    }

    @Override
    public boolean exec(RedisClient client) {
        RedisObject data =  client.getDb()
                .get(client.getArgv()[1]);
        List<String> rangeData;
        if (Arrays.stream(client.getArgv()).anyMatch(e -> e.equalsIgnoreCase("BYSCORE"))) {
            rangeData = ((Zset) data.getData()).rangeByScore(client.getArgv()[2], client.getArgv()[3]);
        }else{
            rangeData = ((Zset) data.getData()).rangeByIndex(client.getArgv()[2], client.getArgv()[3]);
        }
        client.setOutput(Encoder.encodeData(DataType.LINKED_LIST, rangeData));
        return true;
    }

    @Override
    public boolean afterExec(RedisClient client) {
        return true;
    }
}
