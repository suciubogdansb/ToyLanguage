package Module.Containers;

import Module.Exception.DictionaryException;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public interface DictionaryInterface<T1, T2> {
    public T2 get(T1 key) throws DictionaryException;
    public void put(T1 key, T2 value);
    boolean containsKey(T1 key);
    void clear();

    void remove(T1 key);

    Vector<T1> getAll();

    Map<T1, T2> getContent();
}
