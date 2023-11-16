package Module.Containers;

import java.util.List;

public interface ListInterface<T> {
    void add(T element);
    void clear();

    List<T> getAll();
}
