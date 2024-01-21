package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.*;
import com.suciubogdan.demo2.Module.Exception.DictionaryException;
import com.suciubogdan.demo2.Module.Exception.ExpressionException;
import com.suciubogdan.demo2.Module.Exception.TypeException;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

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
        SemaphoreInterface semaphore = state.getSemaphores();
        return new ProgramState(out, forkStack, symbolTableClone, fileTable, statement, heap, semaphore);
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
