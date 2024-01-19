package com.suciubogdan.demo2.Module.Expression;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.HeapInterface;
import com.suciubogdan.demo2.Module.Exception.DictionaryException;
import com.suciubogdan.demo2.Module.Exception.ExpressionException;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class VariableExpression implements ExpressionInterface{
    String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> symbolTable,
                                   HeapInterface<Integer, ValueInterface> heapTable) throws DictionaryException{
        if(!symbolTable.containsKey(id))
            throw new DictionaryException("Variable " + id + " not declared");
        return symbolTable.get(id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public ExpressionInterface deepCopy() {
        return new VariableExpression(id);
    }

    @Override
    public TypeInterface getType(DictionaryInterface<String, TypeInterface> typeTable) throws ExpressionException, DictionaryException {
        if(!typeTable.containsKey(id))
            throw new ExpressionException("Variable " + id + " not declared");
        return typeTable.get(id);
    }
}
