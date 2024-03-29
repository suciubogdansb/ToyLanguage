package Module.Expression;

import Module.Containers.DictionaryInterface;
import Module.Containers.HeapInterface;
import Module.Exception.DictionaryException;
import Module.Exception.DivisionException;
import Module.Exception.ExpressionException;
import Module.Type.TypeInterface;
import Module.Value.ValueInterface;

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
