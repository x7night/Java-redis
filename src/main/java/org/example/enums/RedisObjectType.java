package org.example.enums;

public enum RedisObjectType {
    STRING("string"),
    LIST("list"),
    HASH("hash"),
    SET("set"),
    ZSET("zset"),
    NONE("none");

    private String value;

    RedisObjectType(String value){
        value = value;
    }

    public String getValue(){
        return value;
    }
}
