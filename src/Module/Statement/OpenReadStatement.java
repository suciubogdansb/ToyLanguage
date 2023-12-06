package Module.Statement;

import Module.Containers.StackInterface;
import Module.Exception.*;
import Module.Expression.ExpressionInterface;
import Module.ProgramState;
import Module.Type.StringType;
import Module.Value.StringValue;
import Module.Value.ValueInterface;

public class OpenReadStatement implements StatementInterface {
    private ExpressionInterface expression;

    public OpenReadStatement(ExpressionInterface expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException,
            IOException, HeapException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        ValueInterface value = expression.evaluate(state.getSymbolTable(), state.getHeapTable());
        if (value.getType().equals(new StringType())) {
            StringValue stringValue = (StringValue) value;
            if (state.getFileTable().containsKey(stringValue)) {
                throw new ExpressionException("File " + stringValue.toString() + " already opened");
            } else {
                state.getFileTable().put(stringValue);
            }
        } else {
            throw new ExpressionException("Expression " + expression.toString() + " does not evaluate to a string");
        }
        return state;
    }

    @Override
    public StatementInterface deepCopy() {
        return new OpenReadStatement(expression.deepCopy());
    }
}
