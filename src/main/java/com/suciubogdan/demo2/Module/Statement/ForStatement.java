package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.Expression.ExpressionInterface;
import com.suciubogdan.demo2.Module.Expression.RelationalExpression;
import com.suciubogdan.demo2.Module.Expression.VariableExpression;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.IntType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;

public class ForStatement implements StatementInterface{
    String variableName;
    ExpressionInterface assignedExpression;
    ExpressionInterface compareExpression;
    ExpressionInterface updateExpression;
    StatementInterface statement;

    public ForStatement(String variableName, ExpressionInterface assignedExpression,
                        ExpressionInterface compareExpression, ExpressionInterface updateExpression,
                        StatementInterface statement){
        this.variableName = variableName;
        this.assignedExpression = assignedExpression;
        this.compareExpression = compareExpression;
        this.updateExpression = updateExpression;
        this.statement = statement;
    }

    @Override
    public String toString(){
        return "for("+variableName+"="+assignedExpression+";"+variableName+"<"+compareExpression +";"+
                variableName+"="+updateExpression+") ("+statement+")";
    }
    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException,
            DictionaryException, TypeException, IOException, HeapException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        stack.push(new WhileStatement(new RelationalExpression("<",new VariableExpression(variableName), compareExpression),
                new CompoundStatement(
                        statement,
                        new AssignStatement(variableName, updateExpression)
                )));
        stack.push(new AssignStatement(variableName, assignedExpression));
        stack.push(new VariableStatement(variableName, new IntType()));
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new ForStatement(variableName, assignedExpression.deepCopy(), compareExpression.deepCopy(), updateExpression.deepCopy(), statement.deepCopy());
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws TypeException, ExpressionException, DictionaryException {
        if(typeTable.containsKey(variableName))
            throw new DictionaryException(variableName + " already defined");
        typeTable.put(variableName, new IntType());
        TypeInterface assignedType, compareType, updateType;
        assignedType = assignedExpression.getType(typeTable);
        if(!assignedType.equals(new IntType()))
            throw new TypeException("Assignment expression not int");
        compareType = compareExpression.getType(typeTable);
        if(!compareType.equals(new IntType()))
            throw new TypeException("Compare expression not int");
        updateType = updateExpression.getType(typeTable);
        if(!updateType.equals(new IntType()))
            throw new TypeException("Update expression not int");
        statement.typeCheck(typeTable.deepCopy());
        return typeTable;
    }
}
