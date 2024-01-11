package Module.Statement;
import Module.Containers.DictionaryInterface;
import Module.ProgramState;
import Module.Type.TypeInterface;

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

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable){
        return typeTable;
    }
}
