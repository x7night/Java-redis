package org.example.compoment;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.enums.DataType;
import org.example.enums.RedisObjectType;
@Data
@AllArgsConstructor
public class RedisObject {
    public static final RedisObject NONE = new RedisObject(RedisObjectType.NONE, null, null);
    private RedisObjectType type;
    private DataType encoding;
    private Object data;
}
