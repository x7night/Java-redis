package org.example.datastruct;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.compoment.RedisObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据存储结构
 */
@Data
@NoArgsConstructor
public class Database {
    private Map<String, RedisObject> data = new ConcurrentHashMap<>();

    private Map<String, Long> expires = new HashMap<>();
}
