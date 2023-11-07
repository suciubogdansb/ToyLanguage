package Module.Statement;
import Module.ProgramState;

public class NOPStatement implements StatementInterface{
    public ProgramState execute(ProgramState state){
        return state;
    }

    public String toString() {
        return "";
    }

    @Override
    public StatementInterface deepCopy() {
        return new NOPStatement();
    }
}
