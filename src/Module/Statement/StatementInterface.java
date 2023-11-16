package Module.Statement;

import Module.Exception.*;
import Module.ProgramState;

public interface StatementInterface {
    ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException, TypeException, IOException;

    StatementInterface deepCopy();
}

