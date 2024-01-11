package Module.Statement;

import Module.Containers.DictionaryInterface;
import Module.Exception.*;
import Module.ProgramState;
import Module.Type.TypeInterface;

public interface StatementInterface {
    ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException, TypeException, IOException, HeapException;
    StatementInterface deepCopy();

    DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws TypeException, ExpressionException, DictionaryException;
}

