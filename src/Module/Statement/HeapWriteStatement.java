package Module.Statement;

import Module.Containers.DictionaryInterface;
import Module.Containers.HeapInterface;
import Module.Containers.StackInterface;
import Module.Exception.*;
import Module.Expression.ExpressionInterface;
import Module.ProgramState;
import Module.Type.ReferenceType;
import Module.Value.ReferenceValue;
import Module.Value.ValueInterface;

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
        return state;
    }

    @Override
    public StatementInterface deepCopy() {
        return new HeapWriteStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(%s, %s)".formatted(variableName, expression.toString());
    }
}
