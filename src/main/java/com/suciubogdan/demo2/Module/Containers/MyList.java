package com.suciubogdan.demo2.Module.Containers;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements ListInterface<T>{
    List<T> list;


    public MyList(List<T> list) {
        this.list = list;
    }
    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        list.add(element);
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public List<T> getAll() {
        return list;
    }
}
