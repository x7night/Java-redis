package org.example.datastruct;

import cn.hutool.core.collection.ListUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

public class Zset {
    private ConcurrentSkipListMap<String, String> skipList =  new ConcurrentSkipListMap<>(Comparator.comparingDouble(Double::parseDouble));

    private HashMap<String, String> hash = new HashMap<>();

    public void add(String score, String val){
        skipList.put(score, val);
        hash.put(val, score);
    }

    public String rank(String val){
        return hash.get(val);
    }

    public List<String> rangeByIndex(String from, String to){
        String[] keySet = skipList.navigableKeySet().toArray(new String[0]);
        int begin = Integer.parseInt(from);
        int end = Integer.parseInt(to);
        if (begin > end || begin > keySet.length) {
            return ListUtil.empty();
        }
        List<String> res = new ArrayList<>();
        for (int i = begin; i < end; i++) {
            res.add(skipList.get(keySet[i]));
        }
        return res;
    }

    public List<String> rangeByScore(String from, String to){
        return ListUtil.toList(skipList.subMap(from, to).values());
    }
}
