package Module.Statement;

import Module.Containers.DictionaryInterface;
import Module.Containers.StackInterface;
import Module.Exception.DictionaryException;
import Module.Type.TypeInterface;
import Module.ProgramState;
import Module.Value.ValueInterface;

public class VariableStatement implements StatementInterface{
    String name;
    TypeInterface type;

    public VariableStatement(String name, TypeInterface type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws DictionaryException {
        StackInterface<StatementInterface> stack= state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        if (symbolTable.containsKey(name)) {
            throw new DictionaryException("Variable " + name + " already declared");
        } else {
            symbolTable.put(name, type.defaultValue());
        }
        return state;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public StatementInterface deepCopy() {
        return new VariableStatement(name, type);
    }
}
