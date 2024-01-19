package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.DictionaryException;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class VariableStatement implements StatementInterface{
    String name;
    TypeInterface type;

    public VariableStatement(String name, TypeInterface type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws DictionaryException {
        StackInterface<StatementInterface> stack= state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        if (symbolTable.containsKey(name)) {
            throw new DictionaryException("Variable " + name + " already declared");
        } else {
            symbolTable.put(name, type.defaultValue());
        }
        return null;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public StatementInterface deepCopy() {
        return new VariableStatement(name, type);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) {
        typeTable.put(name, type);
        return typeTable;
    }
}
