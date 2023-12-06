package Module.Statement;

import Module.Containers.DictionaryInterface;
import Module.Containers.HeapInterface;
import Module.Containers.HeapTable;
import Module.Containers.StackInterface;
import Module.Exception.*;
import Module.Expression.ExpressionInterface;
import Module.Expression.ValueExpression;
import Module.ProgramState;
import Module.Type.ReferenceType;
import Module.Type.TypeInterface;
import Module.Value.ReferenceValue;
import Module.Value.ValueInterface;

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
        return state;
    }

    @Override
    public StatementInterface deepCopy() {
        return new HeapAllocStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "new(%s, %s)".formatted(variableName, expression.toString());
    }
}
