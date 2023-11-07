package Module.Containers;

import java.util.List;
import java.util.ArrayList;

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
}
