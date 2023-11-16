package Module.Containers;

import Module.Exception.DictionaryException;
import Module.Exception.IOException;
import Module.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Vector;

public class FileTable implements FileTableInterface{
    DictionaryInterface<StringValue, BufferedReader> fileTable;

    public FileTable(MyDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public FileTable() {
        this.fileTable = new MyDictionary<StringValue, BufferedReader>();
    }

    @Override
    public BufferedReader get(StringValue key) throws DictionaryException {
        return fileTable.get(key);
    }

    @Override
    public void put(StringValue key) throws IOException{
        try{
            BufferedReader fileDescriptor = new BufferedReader(new FileReader(key.getValue()));
            fileTable.put(key, fileDescriptor);
        }
        catch (FileNotFoundException e){
            throw new IOException("File not found");
        }
    }

    @Override
    public boolean containsKey(StringValue key) {
        return fileTable.containsKey(key);
    }

    @Override
    public void close(StringValue key) throws IOException, DictionaryException {
        BufferedReader fileDescriptor = fileTable.get(key);
        try{
            fileDescriptor.close();
        }
        catch (Exception e){
            throw new IOException("Error closing file " + key.toString());
        }
        fileTable.remove(key);
    }

    @Override
    public String toString() {
        return fileTable.toString();
    }

    @Override
    public void clear() {
        fileTable.clear();
    }

    @Override
    public Vector<StringValue> getAll() {
        return fileTable.getAll();
    }
}
