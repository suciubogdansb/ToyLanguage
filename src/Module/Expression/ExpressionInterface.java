package Module.Expression;

import Module.Containers.DictionaryInterface;
import Module.Containers.HeapInterface;
import Module.Exception.DictionaryException;
import Module.Exception.DivisionException;
import Module.Exception.ExpressionException;
import Module.Exception.HeapException;
import Module.Value.ValueInterface;

public interface ExpressionInterface {
    ValueInterface evaluate(DictionaryInterface<String, ValueInterface> symbolTable,
                            HeapInterface<Integer, ValueInterface> heapTable)
            throws DictionaryException, ExpressionException, DivisionException, HeapException;

    ExpressionInterface deepCopy();
}
