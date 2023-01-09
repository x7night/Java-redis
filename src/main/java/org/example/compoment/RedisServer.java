package org.example.compoment;

import lombok.Getter;
import lombok.Setter;
import org.example.App;
import org.example.datastruct.Database;
import org.example.event.FileEvent;
import org.example.handler.RequestHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
@Setter
public class RedisServer {
    /**
     * 客户端集合
     */
    public static Map<SocketChannel, RedisClient> CLIENTS = new HashMap<>();
    /**
     * 数据库集合
     */
    public static List<Database> dbs = new ArrayList<>(8);

    private static ServerSocketChannel serverSocketChannel;
    public static Selector selector;
    private boolean syncAOF = true;
    private long lastAOF;

    private StringBuffer aofBuf = new StringBuffer();

    private BufferedWriter aofFile;

    private Queue<FileEvent> queue = new ConcurrentLinkedQueue<>();

    public RedisServer() throws IOException {
        App.CONTEXT.put("server", this);
        // 初始化aof文件
        Path path = Paths.get("./aof.txt");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        aofFile = Files.newBufferedWriter(path,
                StandardCharsets.UTF_8,
                StandardOpenOption.APPEND);

        // 初始化存储
        for (int i = 0; i < 8; i++) {
            dbs.add(new Database());
        }

        // 获取channel
        serverSocketChannel = ServerSocketChannel.open();
        // 绑定端口
        serverSocketChannel.bind(new InetSocketAddress(19097));
        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);
        // 获取选择器
        selector = Selector.open();
        // 注册accept事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() {
        try {
            if (selector.select(100) > 0) {
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    if (key.isAcceptable()) {
                        connectionHandler();
                    } else if (key.isReadable()) {

                        RedisClient client = (RedisClient) key.attachment();
                        // 生成事件
                        FileEvent fileEvent = FileEvent.builder().client(client).eventType(SelectionKey.OP_READ).requestHandler(RequestHandler.getInstance()).build();
                        // 进入处理队列
                        queue.offer(fileEvent);
                    } else if (key.isWritable()) {
//                            RedisClient client = (RedisClient) key.attachment();
//                            // 生成事件
//                            FileEvent fileEvent = FileEvent.builder().client(client).eventType(SelectionKey.OP_WRITE).requestHandler(RequestHandler.getInstance()).build();
//                            // 进入处理队列
//                            queue.offer(fileEvent);
                        System.out.println("写事件就绪");
                    } else {
                        System.out.println("select key:" + key.interestOps());
                    }
                    keys.remove();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * acceptTcpHandler 连接处理器
     *
     * @throws IOException
     */
    private static void connectionHandler() throws IOException {
        // 获取连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        socketChannel.configureBlocking(false);
        // 生成客户端
        RedisClient client = new RedisClient(socketChannel);
        // 注册读事件
        socketChannel.register(selector, SelectionKey.OP_READ, client);
        System.out.println(socketChannel.getRemoteAddress().toString().substring(1) + " is ok...");
    }


    /**
     * 处理循环
     */
    public void loop() {
        while (true) {
            // 获取事件
            listen();
            // 文件事件处理
            handleFileEvent();
            // 时间时间处理
            handleTimeEvent();
            // 刷盘
            flushAppendOnlyFile();
        }
    }

    /**
     * 文件事件处理(请求,回复)
     */
    private void handleFileEvent() {
        long start = System.currentTimeMillis();
        while (queue.size() > 0 && System.currentTimeMillis() - start < 500) {
            try {
                FileEvent event = queue.poll();
                if (SelectionKey.OP_READ == event.getEventType()) {
                    event.getRequestHandler().handel(event.getClient());
                } else if (SelectionKey.OP_WRITE == event.getEventType()) {
                    event.getReplyHandler().handel(event.getClient());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleTimeEvent() {

    }

    private void flushAppendOnlyFile() {
        if (syncAOF) {
            long curTime = System.currentTimeMillis();
            // 3秒钟一次
            if (lastAOF == 0l || curTime - lastAOF > 3000) {
                try {
                    aofFile.write(aofBuf.toString());
                    aofFile.flush();
                    aofBuf.setLength(0);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                lastAOF = curTime;
            }
        }
    }
}