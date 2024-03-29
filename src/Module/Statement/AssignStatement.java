package Module.Statement;

import Module.Containers.DictionaryInterface;
import Module.Containers.StackInterface;
import Module.Exception.*;
import Module.Expression.ExpressionInterface;
import Module.Expression.ValueExpression;
import Module.ProgramState;
import Module.Type.TypeInterface;
import Module.Value.ValueInterface;

public class AssignStatement implements StatementInterface {
    private String variableName;
    private ExpressionInterface expression;

    public AssignStatement(String variableName, ExpressionInterface expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException,
            TypeException, HeapException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        if (symbolTable.containsKey(variableName)) {
            ValueInterface value = expression.evaluate(symbolTable, state.getHeapTable());
            TypeInterface typeVariable = (symbolTable.get(variableName)).getType();
            if (value.getType().equals(typeVariable)) {
                symbolTable.put(variableName, value);
            } else {
                throw new TypeException("Declared type of variable " + variableName +
                        " and type of the assigned expression do not match");
            }
        } else {
            throw new DictionaryException("Variable " + variableName + " not declared");
        }
        return null;
    }

    public String toString() {
        return variableName + " = " + expression.toString();
    }

    @Override
    public StatementInterface deepCopy() {
        return new AssignStatement(variableName, expression.deepCopy());
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws TypeException, DictionaryException, ExpressionException {
        TypeInterface variableType = typeTable.get(variableName);
        TypeInterface expressionType = expression.getType(typeTable);
        if (!variableType.equals(expressionType)) {
            throw new TypeException("Left and right hand side of the assignment have different types");
        }
        return typeTable;
    }
}
