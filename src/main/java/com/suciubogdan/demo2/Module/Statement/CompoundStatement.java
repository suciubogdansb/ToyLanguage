package com.suciubogdan.demo2.Module.Statement;
import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.DictionaryException;
import com.suciubogdan.demo2.Module.Exception.ExpressionException;
import com.suciubogdan.demo2.Module.Exception.TypeException;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.TypeInterface;

public class CompoundStatement implements StatementInterface{
    private StatementInterface first;
    private StatementInterface second;

    public CompoundStatement(StatementInterface first, StatementInterface second) {
        this.first = first;
        this.second = second;
    }

    public ProgramState execute(ProgramState state) {
        StackInterface<StatementInterface> stack= state.getExecutionStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }

    @Override
    public StatementInterface deepCopy() {
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws TypeException, ExpressionException, DictionaryException {
        return second.typeCheck(first.typeCheck(typeTable));
    }
}
