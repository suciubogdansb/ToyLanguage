package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.SemaphoreInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.IntType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.IntValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class ReleaseStatement implements StatementInterface{
    String variableName;

    public ReleaseStatement(String variableName){
        this.variableName = variableName;
    }
    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException, TypeException, IOException, HeapException, SemaphoreException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        SemaphoreInterface semaphores = state.getSemaphores();
        if(symTable.containsKey(variableName)) {
            ValueInterface varValue = symTable.get(variableName);
            if (varValue.getType().equals(new IntType())) {
                int varInt = ((IntValue) varValue).getValue();
                if (semaphores.containsKey(varInt)) {
                    if(semaphores.containsThread(varInt, state.getId())){
                        semaphores.removeIdentifier(varInt, state.getId());
                    }
                    return null;
                }
                throw new SemaphoreException("Variable not semaphore id");
            }
            throw new DictionaryException("Variable not int");
        }
        throw new DictionaryException("Variable not in symblo table");
    }

    @Override
    public String toString(){return "release("+variableName+")";}

    @Override
    public StatementInterface deepCopy() {
        return new ReleaseStatement(variableName);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws TypeException, ExpressionException, DictionaryException {
        TypeInterface varType = typeTable.get(variableName);
        if(!varType.equals(new IntType()))
            throw new TypeException("Var not int");
        return typeTable;
    }
}
