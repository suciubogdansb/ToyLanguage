package Module.Containers;

import Module.Exception.HeapException;
import Module.Value.ValueInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class HeapTable implements HeapInterface<Integer, ValueInterface>{
    private final Map<Integer, ValueInterface> heapTable;

    public HeapTable() {
        this.heapTable = new HashMap<Integer, ValueInterface>();
    }

    @Override
    public ValueInterface get(Integer key) throws HeapException {
        if(!heapTable.containsKey(key))
            throw new HeapException("Segmentation fault: " + key + " is not a valid address!");
        return heapTable.get(key);
    }

    @Override
    public Integer add(ValueInterface value) throws HeapException {
        int address = IntStream.
                iterate(1, i -> i+1).
                filter(i -> !heapTable.containsKey(i)).
                findFirst().
                orElseThrow(() -> new HeapException("Heap overflow!"));
        heapTable.put(address, value);
        return address;
    }

    @Override
    public void update(Integer key, ValueInterface value) throws HeapException {
        if(!heapTable.containsKey(key))
            throw new HeapException("Segmentation fault: " + key + " is not a valid address!");
        heapTable.put(key, value);
    }
    @Override
    public void clear() {
        heapTable.clear();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Heap table:\n");
        for(Integer key : heapTable.keySet())
            result.append(key.toString()).append(" -> ").append(heapTable.get(key).toString()).append("\n");
        return result.toString();
    }

    @Override
    public boolean containsKey(Integer key) {
        return heapTable.containsKey(key);
    }


    @Override
    public void setContent(Map<Integer, ValueInterface> newContent) {
        heapTable.clear();
        heapTable.putAll(newContent);
    }

    @Override
    public Map<Integer, ValueInterface> getContent() {
        return heapTable;
    }
}
