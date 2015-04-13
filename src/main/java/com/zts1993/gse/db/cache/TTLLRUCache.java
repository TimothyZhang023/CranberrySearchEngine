/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.cache;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 这还是不是TTLCache 我还没有写。。这个是LRUCache
 * Created by TianShuo on 2015/4/9.
 */
public class TTLLRUCache<K, V> implements Serializable {

    private static final int DEFAULT_CAPACITY = 100;

    protected ConcurrentHashMap<K, ValueEntry> map;


    private final transient int maxCapacity;

    private static int MINI_ACCESS = 10;


    public TTLLRUCache() {
        this(DEFAULT_CAPACITY);
    }

    public TTLLRUCache(int capacity) {
        if (capacity <= 0)
            throw new RuntimeException("缓存容量不得小于0");
        this.maxCapacity = capacity;
        this.map = new ConcurrentHashMap<K, ValueEntry>(maxCapacity);
    }


    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    public V put(K key, V value) {
        if ((map.size() > maxCapacity - 1) && !map.containsKey(key)) {
            Set<Map.Entry<K, ValueEntry>> entries = this.map.entrySet();
            removeRencentlyLeastAccess(entries);
        }
        ValueEntry valueEntry = map.put(key, new ValueEntry(value));
        if (valueEntry != null)
            return valueEntry.value;
        else
            return null;
    }

    /**
     * 移除最近最少访问
     */
    protected void removeRencentlyLeastAccess(
            Set<Map.Entry<K, ValueEntry>> entries) {
        // 最小使用次数
        int least = 0;
        // 最久没有被访问
        long earliest = 0;
        K toBeRemovedByCount = null;
        K toBeRemovedByTime = null;
        Iterator<Map.Entry<K, ValueEntry>> it = entries.iterator();
        if (it.hasNext()) {
            Map.Entry<K, ValueEntry> valueEntry = it.next();
            least = valueEntry.getValue().count.get();
            toBeRemovedByCount = valueEntry.getKey();
            earliest = valueEntry.getValue().lastAccess.get();
            toBeRemovedByTime = valueEntry.getKey();
        }
        while (it.hasNext()) {
            Map.Entry<K, ValueEntry> valueEntry = it.next();
            if (valueEntry.getValue().count.get() < least) {
                least = valueEntry.getValue().count.get();
                toBeRemovedByCount = valueEntry.getKey();
            }
            if (valueEntry.getValue().lastAccess.get() < earliest) {
                earliest = valueEntry.getValue().count.get();
                toBeRemovedByTime = valueEntry.getKey();
            }
        }
        // System.out.println("remove:" + toBeRemoved);
        // 如果最少使用次数大于MINI_ACCESS，那么移除访问时间最早的项(也就是最久没有被访问的项）
        if (least > MINI_ACCESS) {
            map.remove(toBeRemovedByTime);
        } else {
            map.remove(toBeRemovedByCount);
        }
    }

    public V get(K key) {
        V value = null;
        ValueEntry valueEntry = map.get(key);
        if (valueEntry != null) {
            // 更新访问时间戳
            valueEntry.updateLastAccess();
            // 更新访问次数
            valueEntry.count.incrementAndGet();
            value = valueEntry.value;
        }
        return value;
    }

    public void clear() {
        map.clear();

    }

    public int size() {
        return map.size();
    }


    public Collection<Map.Entry<K, V>> getAll() {
        Set<K> keys = map.keySet();
        Map<K, V> tmp = new HashMap<K, V>();
        for (K key : keys) {
            tmp.put(key, map.get(key).value);
        }
        return new ArrayList<Map.Entry<K, V>>(tmp.entrySet());
    }


    class ValueEntry implements Serializable {
        private V value;

        private AtomicInteger count;

        private AtomicLong lastAccess;

        public ValueEntry(V value) {
            this.value = value;
            this.count = new AtomicInteger(0);
            lastAccess = new AtomicLong(System.nanoTime());
        }

        public void updateLastAccess() {
            this.lastAccess.set(System.nanoTime());
        }

    }

}
