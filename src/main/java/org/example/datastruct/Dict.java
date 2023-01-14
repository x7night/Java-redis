package org.example.datastruct;

import org.example.compoment.RedisObject;

import java.util.HashMap;

public class Dict<T> extends HashMap<RedisObject<SDS>, T> {

    public Dict() {
        super();
    }


}
