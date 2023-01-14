package org.example.datastruct;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.compoment.RedisObject;
import org.example.enums.RedisObjectType;

/**
 * 数据存储结构
 */
@Data
@NoArgsConstructor
public class Database {
    private final static RedisObject<SDS> KEY = new RedisObject<>(RedisObjectType.STRING, null, new SDS());

    private Dict<RedisObject> dataMap = new Dict<>();

    private Dict<Long> expires = new Dict<>();

    public RedisObject<SDS> getKey(String key) {
        KEY.getData().clear();
        KEY.getData().append(key);
        RedisObject<SDS> realKey = null;
        for (RedisObject<SDS> redisObject : dataMap.keySet()) {
            if (KEY.equals(redisObject)) {
                realKey = redisObject;
                break;
            }
        }
        if (expireIfNeeded(realKey)) {
            realKey = null;
        }
        return realKey;
    }

    private boolean expireIfNeeded(RedisObject<SDS> key) {
        Long expireTime = expires.get(key);
        if (expireTime == null) {
            return false;
        }
        if (expireTime < System.currentTimeMillis()) {
            return true;
        } else {
            key.setLastUpdateTime(System.currentTimeMillis());
            return false;
        }
    }

    /**删除键并向集群传播
     * @return
     */
    private void deleteKeyAndPropagate(){

    }

//    static class StringObject extends RedisObject<SDS>{
//        StringObject(){
//            super(RedisObjectType.STRING, null, new SDS());
//        }
//
//        StringObject(String s){
//            super(RedisObjectType.STRING, null, new SDS(s));
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            RedisObject<?> that = (RedisObject<?>) o;
//            return StrUtil.equals(getData().toString(), that.getData().toString());
//        }
//    }

    public <T> RedisObject put(String key, RedisObject<T> value) {
        RedisObject<SDS> k = getKey(key);
        k = null != k ? k :
                new RedisObject<>(RedisObjectType.STRING, null, new SDS(key));
        return dataMap.put(k, value);
    }

    public RedisObject get(String key) {
        RedisObject<SDS> k = getKey(key);
        return null == k ? RedisObject.NONE : dataMap.getOrDefault(k, RedisObject.NONE);
    }

    public RedisObject getOrDefault(String key, RedisObject defaultValue) {
        RedisObject<SDS> k = getKey(key);
        return null == k ? RedisObject.NONE : dataMap.getOrDefault(k, defaultValue);
    }
}
