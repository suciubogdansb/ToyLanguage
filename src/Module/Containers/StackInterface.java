package Module.Containers;

import Module.Exception.StackException;

public interface StackInterface<T>{
    public T pop() throws StackException;
    public void push(T value);

    boolean isEmpty();
    void clear();
}
