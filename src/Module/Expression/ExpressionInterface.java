package Module.Expression;

import Module.Containers.DictionaryInterface;
import Module.Containers.HeapInterface;
import Module.Exception.*;
import Module.Type.TypeInterface;
import Module.Value.ValueInterface;

public interface ExpressionInterface {
    ValueInterface evaluate(DictionaryInterface<String, ValueInterface> symbolTable,
                            HeapInterface<Integer, ValueInterface> heapTable)
            throws DictionaryException, ExpressionException, DivisionException, HeapException;

    ExpressionInterface deepCopy();

    TypeInterface getType(DictionaryInterface<String, TypeInterface> typeTable) throws ExpressionException, DictionaryException, TypeException;
}
