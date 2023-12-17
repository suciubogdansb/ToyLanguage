package Module.Statement;

import Module.Containers.ListInterface;
import Module.Containers.StackInterface;
import Module.Exception.DictionaryException;
import Module.Exception.DivisionException;
import Module.Exception.ExpressionException;
import Module.Exception.HeapException;
import Module.Expression.ExpressionInterface;
import Module.ProgramState;
import Module.Value.ValueInterface;

public class PrintStatement implements StatementInterface{
    private ExpressionInterface expression;

    public PrintStatement(ExpressionInterface expression) {
        this.expression = expression;
    }

    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException,
            HeapException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        ListInterface<ValueInterface> out = state.getOutput();
        out.add(expression.evaluate(state.getSymbolTable(), state.getHeapTable()));
        return null;
    }

    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public StatementInterface deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }
}
