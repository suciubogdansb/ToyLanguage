package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.Expression.ExpressionInterface;
import com.suciubogdan.demo2.Module.Expression.RelationalExpression;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.TypeInterface;

public class SwitchStatement implements StatementInterface{
    ExpressionInterface condition, exp1, exp2;
    StatementInterface stm1, stm2, stm3;

    public SwitchStatement(ExpressionInterface condition, ExpressionInterface exp1, ExpressionInterface exp2,
                           StatementInterface stm1, StatementInterface stm2,
                           StatementInterface stm3)
    {
        this.condition = condition;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stm1 = stm1;
        this.stm2 = stm2;
        this.stm3 = stm3;
    }

    @Override
    public String toString()
    {
        return "switch(" + condition.toString() + ") case(" + exp1.toString() + "): " + stm1.toString() + " case(" + exp2.toString() + "): " + stm2.toString() + " default: " + stm3.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException, TypeException, IOException, HeapException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        stack.push(new IfStatement(new RelationalExpression("==", condition, exp1),
                stm1,
                new IfStatement(new RelationalExpression("==", condition, exp2),
                        stm2,
                        stm3)));
        return null;
    }

    @Override
    public StatementInterface deepCopy()
    {
        return new SwitchStatement(condition.deepCopy(), exp1.deepCopy(), exp2.deepCopy(), stm1.deepCopy(), stm2.deepCopy(), stm3.deepCopy());
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws TypeException, ExpressionException, DictionaryException {
        return null;
    }


}
