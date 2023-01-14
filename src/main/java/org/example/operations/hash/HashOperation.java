package org.example.operations.hash;

import org.example.enums.RedisObjectType;
import org.example.operations.Operation;

public abstract class HashOperation extends Operation {
    public RedisObjectType getDataType() {
        return RedisObjectType.HASH;
    }
}
