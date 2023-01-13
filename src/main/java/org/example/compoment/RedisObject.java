package org.example.compoment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.enums.DataType;
import org.example.enums.RedisObjectType;

import java.util.Objects;

@Data
@AllArgsConstructor
public class RedisObject<T> {
    public static final RedisObject NONE = new RedisObject(RedisObjectType.NONE, null, null, null);
    /**
     * 数据类型
     */
    private RedisObjectType type;
    /**
     * 编码类型
     */
    private DataType encoding;
    /**
     * 最后升级时间
     */
    private Long lastUpdateTime;
    /**
     * 数据
     */
    private T data;

    public RedisObject(RedisObjectType type, DataType encoding, T data) {
        this.type = type;
        this.encoding = encoding;
        this.data = data;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RedisObject<?> that = (RedisObject<?>) o;
        return type == that.type && encoding == that.encoding && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, encoding, data);
    }
}
