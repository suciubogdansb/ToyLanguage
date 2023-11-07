package Module.Statement;

import Module.Containers.DictionaryInterface;
import Module.Containers.StackInterface;
import Module.Exception.DictionaryException;
import Module.Exception.DivisionException;
import Module.Exception.ExpressionException;
import Module.Exception.TypeException;
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
            TypeException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        if (symbolTable.containsKey(variableName)) {
            ValueInterface value = expression.evaluate(symbolTable);
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
        return state;
    }

    public String toString() {
        return variableName + " = " + expression.toString();
    }

    @Override
    public StatementInterface deepCopy() {
        return new AssignStatement(variableName, expression.deepCopy());
    }
}
