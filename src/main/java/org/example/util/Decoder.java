package org.example.util;

import org.example.compoment.RedisClient;
import org.example.datastruct.SDS;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析命令请求
 */
public class Decoder {
    private final static char CR = '\r';
    private final static char LF = '\n';
    private final static char DOLLAR = '$';
    private final static char ASTERISK = '*';

    private static Map<SocketChannel, SDS> checkpoint = new HashMap<>();

    public static int parser(RedisClient client) {
        // 缓冲区为0
        if (client.getQueryBuffer().length() == 0) {
            return 0;
        }
        // 分割后为0
        String[] lines = client.getQueryBuffer().toString().split("\r\n");
        if (lines.length == 0) {
            return 0;
        }
        int count;
        try {
            count = Integer.parseInt(lines[0], 1, lines[0].length(), 10);
        } catch (NumberFormatException e) {
            // 无法解析成数字
            return 0;
        }
        // 数据不足
        if (count > lines.length) {
            return 0;
        }

        String[] vars = new String[count];
        int removeLen = lines[0].length()+2;
        int idx = 0;
        for (int i = 2; i <= count * 2; i = i + 2) {
            removeLen += lines[i - 1].length();
            removeLen += lines[i].length();
            removeLen += 4;
            vars[idx++] = lines[i];
        }
        client.setArgv(vars);
        client.getQueryBuffer().remove(removeLen);
        return removeLen;
    }

}
