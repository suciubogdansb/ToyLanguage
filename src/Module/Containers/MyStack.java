package Module.Containers;

import Module.Exception.StackException;

import java.util.Stack;

public class MyStack<T> implements StackInterface<T> {
    Stack<T> stack;

    public MyStack(Stack<T> stack) {
        this.stack = stack;
    }
    public MyStack() {
        stack = new Stack<>();
    }
    @Override
    public T pop() throws StackException{
        if(stack.empty())
            throw new StackException("Stack is empty");
        return stack.pop();
    }

    @Override
    public void push(T value){
        stack.push(value);
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public String toString() {
        return stack.toString();
    }

    @Override
    public void clear() {
        stack.clear();
    }
    @Override
    public Stack<T> getAll() {
        Stack<T> copy = new Stack<>();
        for(T element : stack)
            copy.push(element);
        return copy;
    }
}
