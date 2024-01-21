package com.suciubogdan.demo2.Module.Containers;

import com.suciubogdan.demo2.Module.Exception.HeapException;
import com.suciubogdan.demo2.Module.Exception.LockException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LockTable implements LockInterface<Integer, Integer>{
    Map<Integer, AtomicInteger> lockTable;

    public LockTable(){
        lockTable = new HashMap<>();
    }

    @Override
    public Integer lookup(Integer key) {
        return lockTable.get(key).get();
    }

    @Override
    public synchronized Integer union() throws LockException {
        int address = IntStream.
                iterate(1, i -> i+1).
                filter(i -> !lockTable.containsKey(i)).
                findFirst().
                orElseThrow(() -> new LockException("Invalid lock!"));
        lockTable.put(address, new AtomicInteger(-1));
        return address;
    }

    @Override
    public void update(Integer key, Integer identifier) throws LockException {
        if(!lockTable.containsKey(key))
            throw new LockException("Invalid key!");
        lockTable.get(key).set(identifier);
    }

    @Override
    public void update(Integer key) throws LockException {
        if(!lockTable.containsKey(key))
            throw new LockException("Invalid key!");
        lockTable.get(key).set(-1);
    }

    @Override
    public void clear() {
        lockTable.clear();
    }

    @Override
    public synchronized boolean containsKey(Integer key) {
        return lockTable.containsKey(key);
    }

    @Override
    public void setContent(Map<Integer, Integer> newContent) {
        lockTable.clear();
        for(Map.Entry<Integer, Integer> entry: newContent.entrySet()){
            lockTable.put(entry.getKey(), new AtomicInteger(entry.getValue()));
        }
    }

    @Override
    public Map<Integer, Integer> getContent() {
        return lockTable.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                k -> k.getValue().get()));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Heap table:\n");
        for(Integer key : lockTable.keySet())
            result.append(key.toString()).append(" -> ").append(lockTable.get(key).toString()).append("\n");
        return result.toString();
    }
}
