package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.Expression.ExpressionInterface;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.BoolType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.BoolValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class IfStatement implements StatementInterface {
    ExpressionInterface condition;
    StatementInterface ifStatement;
    StatementInterface elseStatement;

    public IfStatement(ExpressionInterface condition, StatementInterface ifStatement, StatementInterface elseStatement){
        this.condition = condition;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    public String toString() {
        return "( if (" + condition.toString() + ") then (" + ifStatement.toString() + ") else (" + elseStatement.toString() + ") )";
    }

    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException,
            TypeException, HeapException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        ValueInterface conditionValue = condition.evaluate(symbolTable, state.getHeapTable());
        if (conditionValue.getType().equals(new BoolType())) {
            BoolValue conditionValueBool = (BoolValue) conditionValue;
            if (conditionValueBool.getValue()) {
                stack.push(ifStatement);
            } else {
                stack.push(elseStatement);
            }
            return null;
        } else {
            throw new TypeException("Condition is not a boolean");
        }
    }

    @Override
    public StatementInterface deepCopy() {
        return new IfStatement(condition.deepCopy(), ifStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws TypeException, DictionaryException, ExpressionException {
        TypeInterface conditionType = condition.getType(typeTable);
        if (!conditionType.equals(new BoolType())) {
            throw new TypeException("If condition is not a boolean");
        }
        ifStatement.typeCheck(typeTable.deepCopy());
        elseStatement.typeCheck(typeTable.deepCopy());
        return typeTable;
    }
}

