package org.example.handler;

import org.example.command.CommandFinder;
import org.example.compoment.RedisClient;
import org.example.datastruct.SDS;
import org.example.util.Decoder;
import org.example.util.Utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SocketChannel;

/**
 * 命令请求处理器
 */
public class RequestHandler {
    private final static RequestHandler INSTANCE = new RequestHandler();

    public static RequestHandler getInstance() {
        return INSTANCE;
    }

    public void handel(RedisClient client) throws Exception {
//        // 读入输入缓冲区
//        readToClientQueryBuf(client);
//        client.setQueryBuffer(readToSDS(client.getSocketChannel()));
//        // 获取命令
//        String[] vars = Parser.parser(client.getQueryBuffer());
//
//        client.setArgv(vars);
//        client.setCmd(CommandFinder.find(vars[0]));
//
//        client.setCmd(CommandFinder.find(client.getArgv()[0]));
//        // 执行
//        client.exec();
//        // 返回结果
//        try {
//            client.getSocketChannel().write(ByteBuffer.wrap (client.getOutput().getBytes()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        // 读入输入缓冲区
        try {
            readToClientQueryBuf(client);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 解析缓冲区
        while(Decoder.parser(client) > 0){
            // 获取命令
            client.setCmd(CommandFinder.find(client.getArgv()[0]));
            // 执行
            try{
                client.exec();
            }catch (Exception e){
                System.out.println("exec fail:" + e.getMessage());
                throw new RuntimeException(e);
            }
            // 返回结果
            try {
                System.out.println("reply:"+client.getOutput());
                client.getSocketChannel().write(ByteBuffer.wrap (client.getOutput().getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void readToClientQueryBuf(RedisClient c) throws IOException {
        if (null != c.getQueryBuffer()) {
            ByteBuffer buf = ByteBuffer.allocate(32);
            int rem = c.getSocketChannel().read(buf);
            while (rem > 0) {
                c.getQueryBuffer().append(buf, rem);
                rem = c.getSocketChannel().read(buf);
            }
        } else {
            c.setQueryBuffer(readToSDS(c.getSocketChannel()));
        }

    }

    private SDS readToSDS(SocketChannel c) throws IOException, NotYetConnectedException {
        ByteBuffer buf = ByteBuffer.allocate(32);
        int n = c.read(buf);
        if (!buf.hasRemaining()) {
            SDS sds = new SDS(buf, Utils.minPowFor(n));
            int rem = c.read(buf);
            while (rem > 0) {
                sds.append(buf, rem);
                rem = c.read(buf);
            }
            return sds;
        } else {
            return new SDS(buf, Utils.minPowFor(n));
        }
    }
}
