package org.example.operations.zset;

import org.example.enums.RedisObjectType;
import org.example.operations.Operation;

public abstract class ZsetOperation extends Operation {
    public RedisObjectType getDataType() {
        return null;
    }
}
