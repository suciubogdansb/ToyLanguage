package Module.Statement;

import Module.Containers.DictionaryInterface;
import Module.Containers.ListInterface;
import Module.Containers.StackInterface;
import Module.Exception.*;
import Module.Expression.ExpressionInterface;
import Module.ProgramState;
import Module.Type.TypeInterface;
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

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws ExpressionException, DictionaryException, TypeException {
        expression.getType(typeTable);
        return typeTable;
    }
}
