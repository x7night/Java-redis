package org.example.util;

import org.example.compoment.RedisObject;
import org.example.datastruct.SDS;
import org.example.enums.DataType;
import org.example.enums.RedisObjectType;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;

/**
 * 编码数据
 */
public class Encoder {
    private final static String CRLF = "\r\n";

    public static String encodeData(RedisObject obj) {
        if (obj.getType().equals(RedisObjectType.NONE)) {
            return encodeData(obj.getEncoding(), null);
        }else{
            return encodeData(obj.getEncoding(), obj.getData());
        }
    }

    public static String encodeData(DataType type, Object data) {
        if (null == data) {
            return "+" + RedisObjectType.NONE.getValue() + CRLF;
        }

        StringBuilder res;
        switch (type) {
            case INTEGER:
                return ":" + data + CRLF;
            case LINKED_LIST:
                res = new StringBuilder();
                List<String> listData = (List) data;
                res.append("*").append(listData.size()).append(CRLF);
                for (int i = 0; i < listData.size(); i++) {
                    res.append("$");
                    res.append(listData.get(i).length()).append(CRLF);
                    res.append(listData.get(i)).append(CRLF);
                }
                return res.toString();
            case HASH_MAP:
                res = new StringBuilder();
                Map<String, String> mapData = (Map) data;
                res.append("*").append(mapData.size() * 2).append(CRLF);
                for (Map.Entry<String, String> entry : mapData.entrySet()) {
                    res.append("$").append(entry.getKey().length()).append(CRLF);
                    res.append(entry.getKey()).append(CRLF);
                    res.append("$").append(entry.getValue().length()).append(CRLF);
                    res.append(entry.getValue()).append(CRLF);
                }
                return res.toString();
            case HASH_SET:
                res = new StringBuilder();
                Set<String> set = (Set) data;
                res.append("*").append(set.size()).append(CRLF);
                for (String s : set) {
                    res.append("$").append(s.length()).append(CRLF)
                            .append(s).append(CRLF);
                }
                return res.toString();
            case SKIP_LIST:
                res = new StringBuilder();
                NavigableMap<String, String> rangeData = (NavigableMap) data;
                res.append("*").append(rangeData.size() * 2).append(CRLF);
                for (Map.Entry<String, String> entry : rangeData.entrySet()) {
                    res.append("$").append(entry.getKey().length()).append(CRLF);
                    res.append(entry.getKey()).append(CRLF);
                    res.append("$").append(entry.getValue().length()).append(CRLF);
                    res.append(entry.getValue()).append(CRLF);
                }
                return res.toString();
            default:
                return "+" + data + CRLF;
        }
    }

    public static String encodeCommand(String[] args){
        StringBuffer commandStr = new StringBuffer();

        return commandStr.toString();
    }
}
