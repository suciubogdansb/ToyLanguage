package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.LatchInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.IntType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.IntValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class AwaitStatement implements StatementInterface{
    private String variableName;

    public AwaitStatement(String var){
        this.variableName = var;
    }

    public String toString(){
        return "await(" + variableName + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException,
            TypeException, IOException, HeapException, LatchException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        LatchInterface<Integer, Integer> latchTable = state.getLatchTable();
        if(symbolTable.containsKey(variableName))
        {
            ValueInterface variableValue = symbolTable.get(variableName);
            if(variableValue.getType().equals(new IntType()))
            {
                IntValue variableInt = (IntValue) variableValue;
                int address = variableInt.getValue();
                int count = latchTable.get(address);
                if(count > 0){
                    stack.push(this);
                }
            }
            else {
                throw new TypeException("Variable " + variableName + " does not represent an integer!");
            }
        }
        else
        {
            throw new DictionaryException("Symbol table does not contain variable " + variableName + "!");
        }
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new AwaitStatement(variableName);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable)
            throws TypeException, ExpressionException, DictionaryException {
        TypeInterface variableType = typeTable.get(variableName);
        if (!variableType.equals(new IntType())) {
            throw new TypeException("Variable " + variableName + " is not of type int");
        }
        return typeTable;
    }
}
