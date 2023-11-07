package Module.Statement;

import Module.Containers.DictionaryInterface;
import Module.Containers.StackInterface;
import Module.Exception.DictionaryException;
import Module.Exception.DivisionException;
import Module.Exception.ExpressionException;
import Module.Exception.TypeException;
import Module.Expression.ExpressionInterface;
import Module.ProgramState;
import Module.Type.BoolType;
import Module.Value.BoolValue;
import Module.Value.ValueInterface;

public class IfStatement implements StatementInterface {
    ExpressionInterface condition;
    StatementInterface ifStatement;
    StatementInterface elseStatement;

    public IfStatement(ExpressionInterface condition, StatementInterface ifStatement, StatementInterface elseStatement){
        this.condition = condition;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    public String toString() {
        return "( if (" + condition.toString() + ") then (" + ifStatement.toString() + ") else (" + elseStatement.toString() + ") )";
    }

    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException,
            TypeException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        ValueInterface conditionValue = condition.evaluate(symbolTable);
        if (conditionValue.getType().equals(new BoolType())) {
            BoolValue conditionValueBool = (BoolValue) conditionValue;
            if (conditionValueBool.getValue()) {
                stack.push(ifStatement);
            } else {
                stack.push(elseStatement);
            }
            return state;
        } else {
            throw new TypeException("Condition is not a boolean");
        }
    }

    @Override
    public StatementInterface deepCopy() {
        return new IfStatement(condition.deepCopy(), ifStatement.deepCopy(), elseStatement.deepCopy());
    }
}

