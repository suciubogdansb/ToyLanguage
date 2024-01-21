package com.suciubogdan.demo2.Module.Containers;

import com.suciubogdan.demo2.Module.Exception.SemaphoreException;
import com.suciubogdan.demo2.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class SemaphoreTable implements SemaphoreInterface{

    Map<Integer, Pair<Integer, List<Integer>>> semaphores;

    public SemaphoreTable()
    {
        this.semaphores = new HashMap<>();
    }
    @Override
    public synchronized Integer union(Integer count) throws SemaphoreException {
        int address = IntStream.
                iterate(1, i -> i+1).
                filter(i -> !semaphores.containsKey(i)).
                findFirst().
                orElseThrow(() -> new SemaphoreException("Heap overflow!"));
        semaphores.put(address, new Pair<>(count, new ArrayList<Integer>()));
        return address;
    }

    @Override
    public boolean containsKey(Integer key) {
        return semaphores.containsKey(key);
    }

    @Override
    public synchronized void add(Integer key, Integer identifier) throws SemaphoreException {
        if(semaphores.get(key).second.contains(identifier))
            throw new SemaphoreException("Sem contains id");
        semaphores.get(key).second.add(identifier);
    }

    @Override
    public synchronized boolean containsThread(Integer key, Integer identifier) {
        return semaphores.get(key).second.contains(identifier);
    }

    @Override
    public synchronized void removeIdentifier(Integer key, Integer identifier) throws SemaphoreException {
        if(!semaphores.get(key).second.contains(identifier))
            throw new SemaphoreException("Sem doesn't contain id");
        semaphores.get(key).second.remove(identifier);
    }

    @Override
    public boolean spaceLeft(Integer key) {
        return semaphores.get(key).first - semaphores.get(key).second.size() > 0;
    }

    @Override
    public void clear() {
        semaphores.clear();
    }

    @Override
    public Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        return semaphores;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Heap table:\n");
        for(Integer key : semaphores.keySet())
            result.append(key.toString()).append(" -> ").append(semaphores.get(key).toString()).append("\n");
        return result.toString();
    }
}
