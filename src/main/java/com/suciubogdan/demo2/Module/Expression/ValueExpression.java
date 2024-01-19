package com.suciubogdan.demo2.Module.Expression;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.HeapInterface;
import com.suciubogdan.demo2.Module.Exception.ExpressionException;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class ValueExpression implements ExpressionInterface {
    ValueInterface value;

    public ValueExpression(ValueInterface value) {
        this.value = value;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> symbolTable,
                                   HeapInterface<Integer, ValueInterface> heapTable) throws ExpressionException {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public ExpressionInterface deepCopy() {
        return new ValueExpression(value.deepCopy());
    }

    @Override
    public TypeInterface getType(DictionaryInterface<String, TypeInterface> typeTable) throws ExpressionException {
        return value.getType();
    }
}
