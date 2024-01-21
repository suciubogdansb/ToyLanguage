package com.suciubogdan.demo2.Module.Containers;

import com.suciubogdan.demo2.Module.Exception.LockException;

import java.util.Map;

public interface LockInterface<T1, T2> {
    T2 lookup(T1 key);
    T1 union() throws LockException;
    void update(T1 key, Integer identifier) throws LockException;

    void update(T1 key) throws LockException;

    void clear();
    boolean containsKey(T1 key);

    void setContent(Map<T1, T2> newContent);

    Map<T1, T2> getContent();
}
