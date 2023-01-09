package org.example;

import org.example.compoment.RedisServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static Map<String, Object> CONTEXT = new HashMap<>();

    public static void main(String[] args) {
        try {
            // 加载命令类


            RedisServer server = new RedisServer();
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(server::loop);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}