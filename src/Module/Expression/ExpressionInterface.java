package Module.Expression;

import Module.Containers.DictionaryInterface;
import Module.Exception.DictionaryException;
import Module.Exception.DivisionException;
import Module.Exception.ExpressionException;
import Module.Value.ValueInterface;

public interface ExpressionInterface {
    ValueInterface evaluate(DictionaryInterface<String, ValueInterface> symbolTable) throws DictionaryException,
            ExpressionException, DivisionException;

    ExpressionInterface deepCopy();
}
