package com.aakkus.dc.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PreventDoubleClickService {

    private Map<String, Pair<Long, Long>> cache = new HashMap<>();

    public boolean isExpried(String key) {
        Pair<Long, Long> doubleClickMethod = cache.get(key);

        if (isNew(doubleClickMethod)) {
            return true;
        }

        if (isExpriedPreventDoubleClickForMethod(key, doubleClickMethod)) {
            return true;
        }

        return false;
    }

    public void put(String key, Pair<Long, Long> value) {
        cache.putIfAbsent(key, value);
    }

    public void flushAll() {
        cache.clear();
    }

    private boolean isExpriedPreventDoubleClickForMethod(String key, Pair<Long, Long> pair) {
        Long puttingTime = pair.getKey();
        Long durationTime = pair.getValue();

        if (isTimeExpried(puttingTime, durationTime)) {
            cache.remove(key);
            return true;
        }
        return false;
    }

    private boolean isTimeExpried(Long puttingTime, Long durationTime) {
        return System.currentTimeMillis() - puttingTime > durationTime;
    }

    private boolean isNew(Pair<Long, Long> pair) {
        return pair == null;
    }
}
