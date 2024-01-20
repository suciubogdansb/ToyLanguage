package com.suciubogdan.demo2.Module.Containers;

import com.suciubogdan.demo2.Module.Exception.LatchException;

import java.util.Map;

public interface LatchInterface<T1, T2> {
    T2 get(T1 key) throws LatchException;

    T1 synchronizedUnion(T2 value) throws LatchException;

    T2 update(T1 key, T2 newValue) throws LatchException;
    void clear();
    boolean containsKey(T1 key);

    Map<T1, T2> getContent();
}
