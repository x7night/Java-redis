package org.example.util;

import org.example.compoment.RedisObject;
import org.example.datastruct.SDS;
import org.example.enums.RedisObjectType;

import java.util.List;
import java.util.Map;

/**
 * 编码数据
 */
public class Encoder {


    public static String encodeData(RedisObject obj) {
        if (obj.getType().equals(RedisObjectType.NONE)) {
            return "+" + RedisObjectType.NONE.getValue() + "\r\n";
        }

        StringBuffer res;
        switch (obj.getEncoding()) {
            case LINKED_LIST:
                res = new StringBuffer();
                List<String> listData = ((List<String>) obj.getData());
                res.append("*").append(listData.size()).append("\r\n");
                for (int i = 0; i < listData.size(); i++) {
                    res.append("$");
                    res.append(listData.get(i).length()).append("\r\n");
                    res.append(listData.get(i)).append("\r\n");
                }
                return res.toString();
            case HASH_MAP:
                res = new StringBuffer();
                Map<String, String> mapData = (Map) obj.getData();
                res.append("*").append(mapData.size()*2).append("\r\n");
                for (Map.Entry<String, String> entry : mapData.entrySet()) {
                    res.append("$").append(entry.getKey().length()).append("\r\n");
                    res.append(entry.getKey()).append("\r\n");
                    res.append("$").append(entry.getValue().length()).append("\r\n");
                    res.append(entry.getValue()).append("\r\n");
                }
                return res.toString();
            default:
                return "+" + ((SDS) obj.getData()).toString() + "\r\n";
        }
    }
}
