package Module.Expression;

import Module.Containers.DictionaryInterface;
import Module.Containers.HeapInterface;
import Module.Exception.*;
import Module.Type.ReferenceType;
import Module.Type.StringType;
import Module.Type.TypeInterface;
import Module.Value.ReferenceValue;
import Module.Value.StringValue;
import Module.Value.ValueInterface;

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
