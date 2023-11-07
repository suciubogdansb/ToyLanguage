package Module;

public class PrintStatement implements StatementInterface{
    private ExpressionInterface expression;

    public PrintStatement(ExpressionInterface expression) {
        this.expression = expression;
    }

    public ProgramState execute(ProgramState state) throws Exception {
        state.getOutput().add(expression.evaluate(state.getSymbolTable(), state.getHeap()));
        return null;
    }

    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
