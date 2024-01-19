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

public class HeapAllocStatement implements StatementInterface {
    private String variableName;
    private ExpressionInterface expression;

    public HeapAllocStatement(String variableName, ExpressionInterface expression) {
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
            throw new DictionaryException("Pointer " + variableName + " not declared");
        }
        ValueInterface variableValue = symbolTable.get(variableName);
        ValueInterface expressionValue = expression.evaluate(symbolTable, heapTable);
        if (!variableValue.getType().equals(new ReferenceType(expressionValue.getType()))) {
            throw new TypeException("Variable " + variableName + " is not a reference type to " + expressionValue.getType().toString());
        }
        int address = heapTable.add(expressionValue);
        symbolTable.put(variableName, new ReferenceValue(address, expressionValue.getType()));
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new HeapAllocStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "new(%s, %s)".formatted(variableName, expression.toString());
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
