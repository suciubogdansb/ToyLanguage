package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.LatchInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.Expression.ExpressionInterface;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.IntType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.IntValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class LatchCreateStatement implements StatementInterface{
    private String variableName;
    private ExpressionInterface expression;

    public LatchCreateStatement(String variableName, ExpressionInterface expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "newLatch(" + variableName + ", " + expression.toString() + ")";
    }

    @Override
    public StatementInterface deepCopy() {
        return new LatchCreateStatement(variableName, expression.deepCopy());
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws DictionaryException, TypeException, ExpressionException {
        if (typeTable.containsKey(variableName)) {
            TypeInterface variableType = typeTable.get(variableName);
            if (!(variableType.equals(new IntType()))) {
                throw new TypeException("Variable " + variableName + "present, but is not of type int");
            }
        }
        else
        {
            typeTable.put(variableName, new IntType());
        }
        TypeInterface expressionType = expression.getType(typeTable);
        if(!(expressionType.equals(new IntType())))
            throw new TypeException("Expression " + expression.toString() + " is not of type int");
        return typeTable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, DictionaryException, TypeException,
            DivisionException, HeapException, LatchException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        ValueInterface value = expression.evaluate(symbolTable, state.getHeapTable());
        if(value.getType().equals(new IntType())) {
            IntValue valueInt = (IntValue) value;
            int number = valueInt.getValue();
            LatchInterface<Integer, Integer> latchTable = state.getLatchTable();
            int address = latchTable.synchronizedUnion(number);
            symbolTable.put(variableName, new IntValue(address));
        } else {
            throw new TypeException("Expression " + expression.toString() + " is not of type int");
        }
        return null;
    }
}
