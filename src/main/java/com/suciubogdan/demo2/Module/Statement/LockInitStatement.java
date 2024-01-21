package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.LockInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.IntType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.IntValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class LockInitStatement implements StatementInterface{
    String variableName;

    public LockInitStatement(String variableName){
        this.variableName = variableName;
    }
    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException, TypeException, IOException, HeapException, LockException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        LockInterface<Integer, Integer> lockTable = state.getLockTable();
        if(symTable.containsKey(variableName))
        {
            ValueInterface value = symTable.get(variableName);
            if(value.getType().equals(new IntType()))
            {
                int address = lockTable.union();
                symTable.put(variableName, new IntValue(address));
                return null;
            }
            else{
                throw new TypeException("Variable not int");
            }
        }
        throw new DictionaryException("Variable not in symbol table");
    }

    @Override
    public StatementInterface deepCopy() {
        return new LockInitStatement(variableName);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws TypeException, ExpressionException, DictionaryException {
        TypeInterface variableType = typeTable.get(variableName);
        if (!variableType.equals(new IntType()))
            throw new TypeException("Variable in lock must be int!");
        return typeTable;
    }
    @Override
    public String toString() {return "newLock("+variableName+")";}
}
