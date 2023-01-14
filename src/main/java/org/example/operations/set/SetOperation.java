package org.example.operations.set;

import org.example.enums.RedisObjectType;
import org.example.operations.Operation;

public abstract class SetOperation extends Operation {
    public RedisObjectType getDataType() {
        return RedisObjectType.SET;
    }
}
