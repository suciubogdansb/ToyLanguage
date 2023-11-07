package Module;

public class CompoundStatement implements StatementInterface{
    private StatementInterface first;
    private StatementInterface second;

    public CompoundStatement(StatementInterface first, StatementInterface second) {
        this.first = first;
        this.second = second;
    }

    public ProgramState execute(ProgramState state) throws Exception {
        state.getExecutionStack().push(second);
        state.getExecutionStack().push(first);
        return null;
    }

    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
}
