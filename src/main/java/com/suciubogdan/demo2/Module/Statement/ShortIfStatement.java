package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.Expression.ExpressionInterface;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.BoolType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;

public class ShortIfStatement implements StatementInterface {
    String variableName;
    ExpressionInterface condition;
    ExpressionInterface trueExpression;
    ExpressionInterface falseExpression;

    public ShortIfStatement(String variableName, ExpressionInterface condition, ExpressionInterface trueExpression,
                            ExpressionInterface falseExpression) {
        this.variableName = variableName;
        this.condition = condition;
        this.falseExpression = falseExpression;
        this.trueExpression = trueExpression;
    }

    @Override
    public String toString() {
        return variableName + "=(" + condition + "):" + trueExpression + "?" + falseExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException, TypeException, IOException, HeapException, LatchException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        stack.push(new IfStatement(this.condition, new AssignStatement(variableName, trueExpression), new AssignStatement(variableName, falseExpression)));
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new ShortIfStatement(variableName, condition.deepCopy(), trueExpression.deepCopy(), falseExpression.deepCopy());
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable)
            throws TypeException, ExpressionException, DictionaryException {
        TypeInterface conditionType = condition.getType(typeTable);
        if(!conditionType.equals(new BoolType()))
            throw new TypeException("Condition not bool");
        TypeInterface variableType = typeTable.get(variableName);
        TypeInterface trueTyep = trueExpression.getType(typeTable);
        if(!trueTyep.equals(variableType))
            throw new TypeException("True condition not the same as variable!");
        TypeInterface flaseType = falseExpression.getType(typeTable);
        if(!flaseType.equals(variableType))
            throw new TypeException("False condition not the same as variable!");
        return typeTable;
    }

}
