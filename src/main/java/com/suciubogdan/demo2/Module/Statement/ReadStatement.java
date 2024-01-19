package com.suciubogdan.demo2.Module.Statement;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Containers.FileTableInterface;
import com.suciubogdan.demo2.Module.Containers.StackInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.Expression.ExpressionInterface;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Type.IntType;
import com.suciubogdan.demo2.Module.Type.StringType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;
import com.suciubogdan.demo2.Module.Value.IntValue;
import com.suciubogdan.demo2.Module.Value.StringValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

import java.io.BufferedReader;

public class ReadStatement implements StatementInterface {
    private ExpressionInterface expression;
    private String variableName;

    public ReadStatement(ExpressionInterface expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return "readFile(" + this.expression + ',' + this.variableName + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws DictionaryException, TypeException, DivisionException,
            ExpressionException, IOException, HeapException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        FileTableInterface fileTable = state.getFileTable();
        if (!symbolTable.containsKey(variableName)) {
            throw new DictionaryException("Variable " + variableName + " not declared");
        }
        if (!symbolTable.get(variableName).getType().equals(new IntType())) {
            throw new TypeException("Variable " + variableName + " is not of type int");
        }
        ValueInterface value = expression.evaluate(symbolTable, state.getHeapTable());
        if (!value.getType().equals(new StringType()))
            throw new ExpressionException("Expression " + expression.toString() + " does not evaluate to a string");
        StringValue stringValue = (StringValue) value;
        if (!fileTable.containsKey(stringValue))
            throw new ExpressionException("File " + stringValue.toString() + " not opened");
        BufferedReader fileDescriptor = fileTable.get(stringValue);
        try {
            String line = fileDescriptor.readLine();
            if (line == null) {
                symbolTable.put(variableName, new IntType().defaultValue());
            } else {
                symbolTable.put(variableName, new IntValue(Integer.parseInt(line)));
            }
        } catch (Exception e) {
            throw new IOException("Error reading from file " + stringValue.toString());
        }
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new ReadStatement(this.expression.deepCopy(), this.variableName);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws ExpressionException, DictionaryException, TypeException {
        TypeInterface expressionType = expression.getType(typeTable);
        if(!expressionType.equals(new StringType()))
            throw new ExpressionException("Expression " + expression.toString() + " does not evaluate to a string");
        TypeInterface variableType = typeTable.get(variableName);
        if(!variableType.equals(new IntType()))
            throw new TypeException("Variable " + variableName + " is not of type int");
        return typeTable;
    }


}
