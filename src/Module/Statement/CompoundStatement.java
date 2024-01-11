package Module.Statement;
import Module.Containers.DictionaryInterface;
import Module.Containers.StackInterface;
import Module.Exception.DictionaryException;
import Module.Exception.ExpressionException;
import Module.Exception.TypeException;
import Module.ProgramState;
import Module.Type.TypeInterface;

public class CompoundStatement implements StatementInterface{
    private StatementInterface first;
    private StatementInterface second;

    public CompoundStatement(StatementInterface first, StatementInterface second) {
        this.first = first;
        this.second = second;
    }

    public ProgramState execute(ProgramState state) {
        StackInterface<StatementInterface> stack= state.getExecutionStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }

    @Override
    public StatementInterface deepCopy() {
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws TypeException, ExpressionException, DictionaryException {
        return second.typeCheck(first.typeCheck(typeTable));
    }
}
