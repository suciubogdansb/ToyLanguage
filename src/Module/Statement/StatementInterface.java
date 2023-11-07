package Module.Statement;
import Module.Exception.DictionaryException;
import Module.Exception.DivisionException;
import Module.Exception.ExpressionException;
import Module.Exception.TypeException;
import Module.ProgramState;

public interface StatementInterface {
    ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException, TypeException;

    StatementInterface deepCopy();
}

