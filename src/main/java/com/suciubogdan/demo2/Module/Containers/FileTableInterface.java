package com.suciubogdan.demo2.Module.Containers;

import com.suciubogdan.demo2.Module.Exception.DictionaryException;
import com.suciubogdan.demo2.Module.Exception.IOException;
import com.suciubogdan.demo2.Module.Value.StringValue;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Vector;

public interface FileTableInterface {
    public BufferedReader get(StringValue key) throws DictionaryException;
    public void put(StringValue key) throws IOException;
    boolean containsKey(StringValue key);

    void close(StringValue key) throws IOException, DictionaryException;
    void clear();

    Vector<StringValue> getAll();

    ArrayList<StringValue> getKeys();
}
