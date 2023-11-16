package Module.Statement;

import Module.Containers.FileTable;
import Module.Containers.FileTableInterface;
import Module.Containers.StackInterface;
import Module.Exception.DictionaryException;
import Module.Exception.DivisionException;
import Module.Exception.ExpressionException;
import Module.Exception.IOException;
import Module.Expression.ExpressionInterface;
import Module.ProgramState;
import Module.Type.StringType;
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
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException, IOException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        FileTableInterface fileTable = state.getFileTable();
        ValueInterface value = expression.evaluate(state.getSymbolTable());
        if(!value.getType().equals(new StringType()))
            throw new ExpressionException("Expression " + expression.toString() + " does not evaluate to a string");
        StringValue stringValue = (StringValue) value;
        if(!fileTable.containsKey(stringValue))
            throw new ExpressionException("File " + stringValue.toString() + " not opened");
        fileTable.close(stringValue);
        return state;
    }
    @Override
    public StatementInterface deepCopy() {
        return new CloseReadStatement(expression.deepCopy());
    }
}
