package Module.Expression;

import Module.Containers.DictionaryInterface;
import Module.Exception.DictionaryException;
import Module.Exception.DivisionException;
import Module.Exception.ExpressionException;
import Module.Value.ValueInterface;

public class VariableExpression implements ExpressionInterface{
    String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> symbolTable) throws DictionaryException{
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
}
