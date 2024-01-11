package Module.Statement;

import Module.Containers.DictionaryInterface;
import Module.Containers.StackInterface;
import Module.Exception.*;
import Module.Expression.ExpressionInterface;
import Module.ProgramState;
import Module.Type.BoolType;
import Module.Type.TypeInterface;
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
            TypeException, HeapException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        ValueInterface conditionValue = condition.evaluate(symbolTable, state.getHeapTable());
        if (conditionValue.getType().equals(new BoolType())) {
            BoolValue conditionValueBool = (BoolValue) conditionValue;
            if (conditionValueBool.getValue()) {
                stack.push(ifStatement);
            } else {
                stack.push(elseStatement);
            }
            return null;
        } else {
            throw new TypeException("Condition is not a boolean");
        }
    }

    @Override
    public StatementInterface deepCopy() {
        return new IfStatement(condition.deepCopy(), ifStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws TypeException, DictionaryException, ExpressionException {
        TypeInterface conditionType = condition.getType(typeTable);
        if (!conditionType.equals(new BoolType())) {
            throw new TypeException("If condition is not a boolean");
        }
        ifStatement.typeCheck(typeTable.deepCopy());
        elseStatement.typeCheck(typeTable.deepCopy());
        return typeTable;
    }
}

