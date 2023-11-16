package Module.Containers;

import Module.Exception.DictionaryException;
import Module.Exception.IOException;
import Module.Value.StringValue;

import java.io.BufferedReader;
import java.util.Vector;

public interface FileTableInterface {
    public BufferedReader get(StringValue key) throws DictionaryException;
    public void put(StringValue key) throws IOException;
    boolean containsKey(StringValue key);

    void close(StringValue key) throws IOException, DictionaryException;
    void clear();

    Vector<StringValue> getAll();
}
