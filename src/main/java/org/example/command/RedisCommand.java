package org.example.command;

import org.example.compoment.RedisClient;

public interface RedisCommand {
    boolean execCommand(RedisClient client);
}
