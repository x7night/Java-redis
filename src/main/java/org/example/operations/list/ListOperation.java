package org.example.operations.list;

import org.example.enums.RedisObjectType;
import org.example.operations.Operation;

public abstract class ListOperation extends Operation {
    @Override
    public RedisObjectType getDataType() {
        return RedisObjectType.LIST;
    }
}
