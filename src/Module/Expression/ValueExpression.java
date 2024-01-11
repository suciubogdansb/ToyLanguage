package Module.Expression;

import Module.Containers.DictionaryInterface;
import Module.Containers.HeapInterface;
import Module.Exception.DivisionException;
import Module.Exception.ExpressionException;
import Module.Type.TypeInterface;
import Module.Value.ValueInterface;
import com.sun.jdi.Value;

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
