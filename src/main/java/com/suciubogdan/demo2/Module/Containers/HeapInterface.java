package com.suciubogdan.demo2.Module.Containers;

import com.suciubogdan.demo2.Module.Exception.HeapException;

import java.util.Map;

public interface HeapInterface <T1, T2>{
    T2 get(T1 key) throws HeapException;
    T1 add(T2 value) throws HeapException;
    void update(T1 key, T2 value) throws HeapException;
    void clear();
    boolean containsKey(T1 key);

    void setContent(Map<T1, T2> newContent);

    Map<T1, T2> getContent();
}
