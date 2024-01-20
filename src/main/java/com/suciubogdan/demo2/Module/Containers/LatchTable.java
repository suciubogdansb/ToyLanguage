package com.suciubogdan.demo2.Module.Containers;


import com.suciubogdan.demo2.Module.Exception.HeapException;
import com.suciubogdan.demo2.Module.Exception.LatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class LatchTable implements LatchInterface<Integer, Integer>{

    private final Map<Integer, Integer> latchTable;

    public LatchTable() {
        this.latchTable = new HashMap<Integer, Integer>();
    }
    @Override
    public synchronized Integer get(Integer key) throws LatchException {
        if (!this.latchTable.containsKey(key))
            throw new LatchException("Latch table does not contain key: " + key);
        return this.latchTable.get(key);
    }

    @Override
    public synchronized Integer synchronizedUnion(Integer value) throws LatchException {
        int address = IntStream.
                iterate(1, i -> i+1).
                filter(i -> !latchTable.containsKey(i)).
                findFirst().
                orElseThrow(() -> new LatchException("Cannot add new latch!"));
        this.latchTable.put(address, value);
        return address;
    }

    @Override
    public void clear() {
        this.latchTable.clear();
    }

    @Override
    public synchronized Integer update(Integer key, Integer newValue) throws LatchException {
        if (!this.latchTable.containsKey(key))
            throw new LatchException("Latch table does not contain key: " + key);
        Integer value = this.latchTable.get(key);
        this.latchTable.put(key, newValue);
        return value;
    }

    @Override
    public synchronized boolean containsKey(Integer key) {
        return this.latchTable.containsKey(key);
    }
    @Override
    public Map<Integer, Integer> getContent() {
        return this.latchTable;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Latch table:\n");
        for(Integer key : latchTable.keySet())
            result.append(key.toString()).append(" -> ").append(latchTable.get(key).toString()).append("\n");
        return result.toString();
    }
}
