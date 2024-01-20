package com.suciubogdan.demo2.Module.Expression;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.HeapInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.Type.BoolType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.BoolValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class NotExpression implements ExpressionInterface{
    ExpressionInterface expression;

    public NotExpression(ExpressionInterface expression){
        this.expression = expression;
    }

    @Override
    public String toString()
    {
        return "!("+expression.toString()+')';
    }
    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> symbolTable, HeapInterface<Integer, ValueInterface> heapTable) throws DictionaryException, ExpressionException, DivisionException, HeapException {
        ValueInterface evaluatedResult = expression.evaluate(symbolTable,heapTable);
        if(evaluatedResult.getType().equals(new BoolType()))
        {
            BoolValue boolValue = (BoolValue) evaluatedResult;
            boolean boolResult = boolValue.getValue();
            return new BoolValue(!boolResult);
        }
        else
        {
            throw new ExpressionException("Expression not bool!");
        }
    }

    @Override
    public ExpressionInterface deepCopy() {
        return new NotExpression(expression.deepCopy());
    }

    @Override
    public TypeInterface getType(DictionaryInterface<String, TypeInterface> typeTable) throws ExpressionException, DictionaryException, TypeException {
        TypeInterface expressionType = expression.getType(typeTable);
        if (!(expressionType.equals(new BoolType())))
            throw new ExpressionException("Expression not bool!");
        return new BoolType();
    }
}
