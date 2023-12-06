package Module.Containers;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MyDictionary<T1, T2> implements DictionaryInterface<T1, T2>{
    Map<T1, T2> dictionary;

    public MyDictionary(Map<T1, T2> dictionary) {
        this.dictionary = dictionary;
    }

    public MyDictionary() {
        this.dictionary = new HashMap<>();
    }
    @Override
    public T2 get(T1 key) {
        return dictionary.get(key);
    }

    @Override
    public void put(T1 key, T2 value) {
        dictionary.put(key, value);
    }

    @Override
    public boolean containsKey(T1 key) {
        return dictionary.containsKey(key);
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }

    @Override
    public void clear() {
        dictionary.clear();
    }

    @Override
    public void remove(T1 key) {
        dictionary.remove(key);
    }

    @Override
    public Vector<T1> getAll() {
        Vector<T1> keys = new Vector<>();
        for(T1 key : dictionary.keySet())
            keys.add(key);
        return keys;
    }

    @Override
    public Map<T1, T2> getContent() {
        return dictionary;
    }
}
