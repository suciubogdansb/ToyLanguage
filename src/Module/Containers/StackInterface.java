package Module.Containers;

import Module.Exception.StackException;

import java.util.Stack;
import java.util.Vector;

public interface StackInterface<T>{
    public T pop() throws StackException;
    public void push(T value);

    boolean isEmpty();
    void clear();

    Stack<T> getAll();
}
