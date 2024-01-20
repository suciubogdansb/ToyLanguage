package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.Expression.ExpressionInterface;
import com.suciubogdan.demo2.Module.Expression.NotExpression;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.BoolType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;

public class RepeatStatement implements StatementInterface{
    ExpressionInterface condition;
    StatementInterface statement;

    public RepeatStatement(ExpressionInterface expression, StatementInterface statement) {
        this.condition = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException, TypeException, IOException, HeapException, LatchException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        stack.push(new WhileStatement(new NotExpression(this.condition), this.statement));
        stack.push(this.statement);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new RepeatStatement(condition.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString(){
        return "repeat (" + statement.toString() +") until ("+condition.toString()+")";
    }
    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws TypeException, ExpressionException, DictionaryException {
        TypeInterface conditionType = condition.getType(typeTable);
        if (!conditionType.equals(new BoolType())) {
            throw new ExpressionException("Repeat condition is not a boolean");
        }
        statement.typeCheck(typeTable.deepCopy());
        return typeTable;
    }
}
