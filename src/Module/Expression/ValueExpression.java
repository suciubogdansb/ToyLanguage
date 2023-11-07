package Module.Expression;

import Module.Containers.DictionaryInterface;
import Module.Exception.DivisionException;
import Module.Exception.ExpressionException;
import Module.Value.ValueInterface;
import com.sun.jdi.Value;

public class ValueExpression implements ExpressionInterface {
    ValueInterface value;

    public ValueExpression(ValueInterface value) {
        this.value = value;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> symbolTable) throws ExpressionException {
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
}
