package Module;

public class AssignStatement implements StatementInterface{
    private String variableName;
    private ExpressionInterface expression;

    public AssignStatement(String variableName, ExpressionInterface expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    public ProgramState execute(ProgramState state) throws Exception {
        state.getSymbolTable().put(variableName, expression.evaluate(state.getSymbolTable(), state.getHeap()));
        return null;
    }

    public String toString() {
        return variableName + " = " + expression.toString();
    }
}
