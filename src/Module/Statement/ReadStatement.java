package Module.Statement;

import Module.Containers.DictionaryInterface;
import Module.Containers.FileTable;
import Module.Containers.FileTableInterface;
import Module.Containers.StackInterface;
import Module.Exception.*;
import Module.Expression.ExpressionInterface;
import Module.ProgramState;
import Module.Type.IntType;
import Module.Type.StringType;
import Module.Value.IntValue;
import Module.Value.StringValue;
import Module.Value.ValueInterface;

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


}
