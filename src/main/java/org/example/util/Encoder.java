package org.example.util;

import org.example.compoment.RedisObject;
import org.example.datastruct.SDS;
import org.example.enums.RedisObjectType;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 编码数据
 */
public class Encoder {
    private final static String CRLF = "\r\n";

    public static String encodeData(RedisObject obj) {
        if (obj.getType().equals(RedisObjectType.NONE)) {
            return "+" + RedisObjectType.NONE.getValue() + CRLF;
        }

        StringBuffer res;
        switch (obj.getEncoding()) {
            case LINKED_LIST:
                res = new StringBuffer();
                List<String> listData = ((List<String>) obj.getData());
                res.append("*").append(listData.size()).append(CRLF);
                for (int i = 0; i < listData.size(); i++) {
                    res.append("$");
                    res.append(listData.get(i).length()).append(CRLF);
                    res.append(listData.get(i)).append(CRLF);
                }
                return res.toString();
            case HASH_MAP:
                res = new StringBuffer();
                Map<String, String> mapData = (Map) obj.getData();
                res.append("*").append(mapData.size() * 2).append(CRLF);
                for (Map.Entry<String, String> entry : mapData.entrySet()) {
                    res.append("$").append(entry.getKey().length()).append(CRLF);
                    res.append(entry.getKey()).append(CRLF);
                    res.append("$").append(entry.getValue().length()).append(CRLF);
                    res.append(entry.getValue()).append(CRLF);
                }
                return res.toString();
            case HASH_SET:
                res = new StringBuffer();
                Set<String> set = (Set) obj.getData();
                res.append("*").append(set.size()).append(CRLF);
                for (String s : set) {
                    res.append("$").append(s.length()).append(CRLF);
                }
                return res.toString();
            default:
                return "+" + ((SDS) obj.getData()).toString() + CRLF;
        }
    }

    public String encodeCommand(String[] args){
        StringBuffer commandStr = new StringBuffer();

        return commandStr.toString();
    }
}
