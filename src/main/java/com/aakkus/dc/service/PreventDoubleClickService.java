package com.aakkus.dc.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PreventDoubleClickService {

    private Map<String, Pair<Long, Long>> cache = new HashMap<>();

    public boolean isExpired(String key) {
        Pair<Long, Long> doubleClickMethod = cache.get(key);

        if (isNew(doubleClickMethod)) {
            return true;
        }

        if (isExpiredPreventDoubleClickForMethod(key, doubleClickMethod)) {
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

    public void flushByKey(String key) {
        cache.remove(key);
    }

    private boolean isExpiredPreventDoubleClickForMethod(String key, Pair<Long, Long> pair) {
        Long puttingTime = pair.getKey();
        Long durationTime = pair.getValue();

        if (isTimeExpired(puttingTime, durationTime)) {
            cache.remove(key);
            return true;
        }
        return false;
    }

    private boolean isTimeExpired(Long puttingTime, Long durationTime) {
        return System.currentTimeMillis() - puttingTime > durationTime;
    }

    private boolean isNew(Pair<Long, Long> pair) {
        return pair == null;
    }
}
