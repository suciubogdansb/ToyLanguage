package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.LatchInterface;
import com.suciubogdan.demo2.Module.Containers.ListInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.IntType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.IntValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class CountDownStatement implements StatementInterface{

    private String variableName;

    public CountDownStatement(String variableName) {
        this.variableName = variableName;
    }
    @Override
    public String toString() {return "countDown(" + variableName+ ")";}

    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException,
            DictionaryException, TypeException, IOException, HeapException, LatchException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        LatchInterface<Integer, Integer> latchTable = state.getLatchTable();
        ListInterface<ValueInterface> output = state.getOutput();
        if(symbolTable.containsKey(variableName))
        {
            ValueInterface variableValue = symbolTable.get(variableName);
            if(variableValue.getType().equals(new IntType()))
            {
                IntValue variableInt = (IntValue) variableValue;
                int address = variableInt.getValue();
                if (latchTable.containsKey(address))
                {
                    int count = latchTable.get(address);
                    if(count > 0){
                        output.add(new IntValue(state.getId()));
                        latchTable.update(address, count-1);
                    }
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
        return new CountDownStatement(variableName);
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
