package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.HeapInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.Expression.ExpressionInterface;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.ReferenceType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.ReferenceValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class HeapWriteStatement implements StatementInterface{
    String variableName;
    ExpressionInterface expression;

    public HeapWriteStatement(String variableName, ExpressionInterface expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException,
            DictionaryException, TypeException, IOException, HeapException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        HeapInterface<Integer, ValueInterface> heapTable = state.getHeapTable();
        if (!symbolTable.containsKey(variableName)) {
            throw new DictionaryException("Variable " + variableName + " not declared");
        }
        ValueInterface variableValue = symbolTable.get(variableName);
        if (!(variableValue.getType() instanceof ReferenceType)) {
            throw new TypeException("Variable " + variableName + " is not a reference type");
        }
        ReferenceValue referenceValue = (ReferenceValue) variableValue;
        int referenceAddress = referenceValue.getAddress();
        if (!heapTable.containsKey(referenceAddress)) {
            throw new HeapException("Address " + referenceAddress + " not found in heap");
        }
        ValueInterface expressionValue = expression.evaluate(symbolTable, heapTable);
        if (!expressionValue.getType().equals(referenceValue.getLocationType())) {
            throw new TypeException("Variable " + variableName + " is not a reference type to " + expressionValue.getType().toString());
        }
        heapTable.update(referenceAddress, expressionValue);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new HeapWriteStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(%s, %s)".formatted(variableName, expression.toString());
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws ExpressionException, DictionaryException, TypeException {
        TypeInterface variableType = typeTable.get(variableName);
        TypeInterface expressionType = expression.getType(typeTable);
        if (!variableType.equals(new ReferenceType(expressionType))) {
            throw new TypeException("Variable " + variableName + " is not a reference type to " + expressionType.toString());
        }
        return typeTable;
    }
}