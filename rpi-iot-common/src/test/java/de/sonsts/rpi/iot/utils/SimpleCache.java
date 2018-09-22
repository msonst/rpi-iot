package de.sonsts.rpi.iot.utils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleCache
{

    private List<CacheEntry> cache = new CopyOnWriteArrayList<CacheEntry>();
    private AtomicInteger counter = new AtomicInteger(0);

    public void update(String text)
    {
        cache.add(new CacheEntry(text, counter.getAndIncrement()));
    }

    public List<CacheEntry> getContent()
    {
        return Collections.unmodifiableList(cache);
    }
}
