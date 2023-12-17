package Module.Statement;
import Module.Containers.StackInterface;
import Module.ProgramState;

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
}
