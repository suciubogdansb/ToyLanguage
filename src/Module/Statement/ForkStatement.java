package Module.Statement;

import Module.Containers.*;
import Module.Exception.DictionaryException;
import Module.Exception.ExpressionException;
import Module.Exception.TypeException;
import Module.ProgramState;
import Module.Type.TypeInterface;
import Module.Value.ValueInterface;

public class ForkStatement implements StatementInterface {
    private StatementInterface statement;

    public ForkStatement(StatementInterface statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        ListInterface<ValueInterface> out = state.getOutput();
        StackInterface<StatementInterface> forkStack = new MyStack<>();
        DictionaryInterface<String, ValueInterface> symbolTableClone = state.getSymbolTable().deepCopy();
        FileTableInterface fileTable = state.getFileTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeapTable();
        return new ProgramState(out, forkStack, symbolTableClone, fileTable, statement, heap);
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }

    @Override
    public StatementInterface deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable)
            throws TypeException, ExpressionException, DictionaryException {
        statement.typeCheck(typeTable.deepCopy());
        return typeTable;
    }
}
