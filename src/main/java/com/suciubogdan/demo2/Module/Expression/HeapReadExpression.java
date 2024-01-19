package com.suciubogdan.demo2.Module.Expression;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.HeapInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.Type.ReferenceType;
import com.suciubogdan.demo2.Module.Type.StringType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.ReferenceValue;
import com.suciubogdan.demo2.Module.Value.StringValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class HeapReadExpression implements ExpressionInterface{
    ExpressionInterface variableName;

    public HeapReadExpression(ExpressionInterface variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return "rH(" + variableName + ")";
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> symbolTable,
                                   HeapInterface<Integer, ValueInterface> heapTable)
            throws DictionaryException, ExpressionException, DivisionException, HeapException {
        ValueInterface variableNameValue = variableName.evaluate(symbolTable, heapTable);
        if (variableNameValue.getType() instanceof ReferenceType) {
            ReferenceValue referenceValue = (ReferenceValue) variableNameValue;
            int address = referenceValue.getAddress();
            return heapTable.get(address);
        }
        if (!(variableNameValue.getType().equals(new StringType()))) {
            throw new ExpressionException("Variable " + variableName + " is not a string type");
        }
        String variableName = ((StringValue) variableNameValue).getValue();
        ValueInterface variableValue = symbolTable.get(variableName);
        if (!(variableValue.getType() instanceof ReferenceType)) {
            throw new ExpressionException("Variable " + variableName + " is not a reference type");
        }
        ReferenceValue referenceValue = (ReferenceValue) variableValue;
        int address = referenceValue.getAddress();
        return heapTable.get(address);
    }

    @Override
    public ExpressionInterface deepCopy() {
        return new HeapReadExpression(variableName);
    }

    @Override
    public TypeInterface getType(DictionaryInterface<String, TypeInterface> typeTable) throws ExpressionException, DictionaryException, TypeException {
        TypeInterface variableNameType = variableName.getType(typeTable);
        if (variableNameType instanceof ReferenceType referenceType) {
            return referenceType.getInner();
        }
        throw new TypeException("Variable " + variableName + " is not a reference type");
    }
}
