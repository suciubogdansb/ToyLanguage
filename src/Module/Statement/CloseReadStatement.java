package Module.Statement;

import Module.Containers.DictionaryInterface;
import Module.Containers.FileTable;
import Module.Containers.FileTableInterface;
import Module.Containers.StackInterface;
import Module.Exception.*;
import Module.Expression.ExpressionInterface;
import Module.ProgramState;
import Module.Type.StringType;
import Module.Type.TypeInterface;
import Module.Value.StringValue;
import Module.Value.ValueInterface;

public class CloseReadStatement implements StatementInterface{
    private ExpressionInterface expression;

    public CloseReadStatement(ExpressionInterface expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException,
            IOException, HeapException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        FileTableInterface fileTable = state.getFileTable();
        ValueInterface value = expression.evaluate(state.getSymbolTable(), state.getHeapTable());
        if(!value.getType().equals(new StringType()))
            throw new ExpressionException("Expression " + expression.toString() + " does not evaluate to a string");
        StringValue stringValue = (StringValue) value;
        if(!fileTable.containsKey(stringValue))
            throw new ExpressionException("File " + stringValue.toString() + " not opened");
        fileTable.close(stringValue);
        return null;
    }
    @Override
    public StatementInterface deepCopy() {
        return new CloseReadStatement(expression.deepCopy());
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable)
            throws ExpressionException, DictionaryException, TypeException {
        TypeInterface expressionType = expression.getType(typeTable);
        if(!expressionType.equals(new StringType()))
            throw new ExpressionException("Expression " + expression.toString() + " does not evaluate to a string");
        return typeTable;
    }
}
