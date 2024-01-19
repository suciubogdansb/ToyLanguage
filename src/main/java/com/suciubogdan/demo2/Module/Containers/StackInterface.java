package com.suciubogdan.demo2.Module.Containers;

import com.suciubogdan.demo2.Module.Exception.StackException;

import java.util.Stack;

public interface StackInterface<T>{
    public T pop() throws StackException;
    public void push(T value);

    boolean isEmpty();
    void clear();

    Stack<T> getAll();
}
