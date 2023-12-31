package Module.Statement;
import Module.ProgramState;

public class NOPStatement implements StatementInterface{
    public ProgramState execute(ProgramState state){
        return null;
    }

    public NOPStatement(){}

    public String toString() {
        return "NOP";
    }

    @Override
    public StatementInterface deepCopy() {
        return new NOPStatement();
    }
}
