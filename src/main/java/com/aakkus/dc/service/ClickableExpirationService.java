package com.aakkus.dc.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClickableExpirationService {

    Map<String, Pair<Long, Pair<Long, Object>>> cache = new HashMap<>();

    public boolean isExpried(String key) {
        Pair<Long, Pair<Long, Object>> pair = cache.get(key);

        return isClickableRequestNotExist(pair) || checkExpriationForRequest(key, pair);
    }

    private boolean checkExpriationForRequest(String key, Pair<Long, Pair<Long, Object>> pair) {
        Long puttingTime = pair.getKey();
        Long durationTime = pair.getValue().getKey();

        if (isTimeExpried(puttingTime, durationTime)) {
            cache.remove(key);
            return true;
        }
        return false;
    }

    public void put(String key, Pair<Long, Pair<Long, Object>> value) {
        cache.putIfAbsent(key, value);
    }

    public void flushAll(){
        cache.clear();
    }

    private boolean isTimeExpried(Long puttingTime, Long durationTime) {
        return System.currentTimeMillis() - puttingTime > durationTime;
    }

    private boolean isClickableRequestNotExist(Pair<Long, Pair<Long, Object>> pair) {
        return pair == null;
    }
}
