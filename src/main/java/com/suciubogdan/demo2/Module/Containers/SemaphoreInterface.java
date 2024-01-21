package com.suciubogdan.demo2.Module.Containers;

import com.suciubogdan.demo2.Module.Exception.SemaphoreException;
import com.suciubogdan.demo2.Pair;

import java.util.List;
import java.util.Map;

public interface SemaphoreInterface{
    Integer union(Integer count) throws SemaphoreException;

    boolean containsKey(Integer key);

    void add(Integer key, Integer identifier) throws SemaphoreException;

    boolean containsThread(Integer key, Integer identifier);

    void removeIdentifier(Integer key, Integer identifier) throws SemaphoreException;

    boolean spaceLeft(Integer key);

    void clear();
    Map<Integer, Pair<Integer, List<Integer>>> getContent();
}
