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

public class UnlockStatement implements StatementInterface{

    String variableName;

    public UnlockStatement(String variableName)
    {
        this.variableName = variableName;
    }


    @Override
    public String toString() {
        return "unlock(" + variableName + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException, TypeException, IOException, HeapException, LockException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        LockInterface<Integer, Integer> lockTable = state.getLockTable();
        if(symTable.containsKey(variableName)){
            ValueInterface valueVariable = symTable.get(variableName);
            if(valueVariable.getType().equals(new IntType())){
                int address = ((IntValue) valueVariable).getValue();
                if(lockTable.containsKey(address)){
                    int value = lockTable.lookup(address);
                    if(value == state.getId())
                        lockTable.update(address);
                    return null;
                }
                throw new LockException("Wrong lock address");
            }
            throw new TypeException("Variable not int");
        }
        throw new DictionaryException("No such variable");
    }

    @Override
    public StatementInterface deepCopy() {
        return new UnlockStatement(variableName);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws TypeException, ExpressionException, DictionaryException {
        TypeInterface variableType = typeTable.get(variableName);
        if (!variableType.equals(new IntType()))
            throw new TypeException("Variable in lock must be int!");
        return typeTable;
    }
}
