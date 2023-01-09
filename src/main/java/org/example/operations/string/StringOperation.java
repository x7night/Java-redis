package org.example.operations.string;

import org.example.annotation.Command;
import org.example.enums.RedisObjectType;
import org.example.operations.Operation;

@Command
public abstract class StringOperation extends Operation {

    public RedisObjectType getDataType() {
        return RedisObjectType.STRING;
    }

}
