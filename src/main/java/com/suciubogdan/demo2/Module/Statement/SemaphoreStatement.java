package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.HeapInterface;
import com.suciubogdan.demo2.Module.Containers.SemaphoreInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.Expression.ExpressionInterface;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.IntType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.IntValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class SemaphoreStatement implements StatementInterface{
    String variableName;

    ExpressionInterface expression;

    public SemaphoreStatement(String variableName, ExpressionInterface expression)
    {
        this.variableName = variableName;
        this.expression = expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException, TypeException, IOException, HeapException, SemaphoreException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        SemaphoreInterface semaphores = state.getSemaphores();
        HeapInterface<Integer, ValueInterface> heap = state.getHeapTable();
        ValueInterface varValue = symTable.get(variableName);
        if(varValue.getType().equals(new IntType())){
            ValueInterface expValue = expression.evaluate(symTable, heap);
            if(expValue.getType().equals(new IntType())){
                int expInt = ((IntValue) expValue).getValue();
                int address = semaphores.union(expInt);
                symTable.put(variableName, new IntValue(address));
                return null;
            }
            throw new TypeException("Exp not int");
        }
        throw new TypeException("Var not int");
    }

    @Override
    public StatementInterface deepCopy() {
        return new SemaphoreStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString(){
        return "semaphore("+variableName+","+expression+")";
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws TypeException, ExpressionException, DictionaryException {
        TypeInterface varType = typeTable.get(variableName);
        if(!varType.equals(new IntType()))
            throw new TypeException("Var not int");
        TypeInterface expType = expression.getType(typeTable);
        if(!expType.equals(new IntType()))
            throw new TypeException("Exp not int");
        return typeTable;
    }
}
