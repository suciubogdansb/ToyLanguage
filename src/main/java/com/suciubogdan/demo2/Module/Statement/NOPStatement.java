package com.suciubogdan.demo2.Module.Statement;
import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.TypeInterface;

public class NOPStatement implements StatementInterface{
    public ProgramState execute(ProgramState state){
        return null;
    }

    public NOPStatement(){}

    public String toString() {
        return "NOP";
    }

    @Override
    public StatementInterface deepCopy() {
        return new NOPStatement();
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable){
        return typeTable;
    }
}
