package Module;

import Module.Containers.*;
import Module.Exception.*;
import Module.Statement.StatementInterface;
import Module.Value.StringValue;
import Module.Value.ValueInterface;
import com.sun.jdi.Value;

import java.util.Stack;

public class ProgramState {
    ListInterface<ValueInterface> out;
    StackInterface<StatementInterface> executionStack;
    DictionaryInterface<String, ValueInterface> symbolTable;
    FileTableInterface fileTable;
    HeapInterface<Integer, ValueInterface> heapTable;
    StatementInterface originalProgram;

    static int counter = 0;
    int id;

    private static synchronized Integer getCounter() {
        return counter++;
    }

    public ProgramState(ListInterface<ValueInterface> out, StackInterface<StatementInterface> executionStack,
                 DictionaryInterface<String, ValueInterface> symbolTable, FileTableInterface fileTable,
                 StatementInterface originalProgram, HeapInterface<Integer, ValueInterface> heapTable) {
        this.out = out;
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram.deepCopy();
        this.heapTable = heapTable;
        this.id = getCounter();
        this.executionStack.push(originalProgram);
    }

    public ProgramState(StatementInterface originalProgram) {
        this.out = new MyList<ValueInterface>();
        this.executionStack = new MyStack<StatementInterface>();
        this.symbolTable = new MyDictionary<String, ValueInterface>();
        this.fileTable = new FileTable();
        this.heapTable = new HeapTable();
        this.originalProgram = originalProgram.deepCopy();
        this.id = getCounter();
        this.executionStack.push(originalProgram);
    }

    //Getters and setters for the stack, symbol table and output list
    public StackInterface<StatementInterface> getExecutionStack() {
        return executionStack;
    }

    public DictionaryInterface<String, ValueInterface> getSymbolTable() {
        return symbolTable;
    }

    public ListInterface<ValueInterface> getOutput() {
        return out;
    }

    public FileTableInterface getFileTable() {
        return fileTable;
    }

    public HeapInterface<Integer, ValueInterface> getHeapTable() {
        return heapTable;
    }

    public void setExecutionStack(StackInterface<StatementInterface> executionStack) {
        this.executionStack = executionStack;
    }

    public void setSymbolTable(DictionaryInterface<String, ValueInterface> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void setOutput(ListInterface<ValueInterface> out) {
        this.out = out;
    }

    public void setFileTable(FileTableInterface fileTable) {
        this.fileTable = fileTable;
    }

    public void setHeapTable(HeapInterface<Integer, ValueInterface> heapTable) {
        this.heapTable = heapTable;
    }

    @Override
    public String toString() {
        String programStateString = "------------------------------------------------\n";
        programStateString += "Program state " + id + ":\n";
        StringBuilder ExecutionStackString = new StringBuilder("ExeStack:\n");
        Stack<StatementInterface> executionStackCopy = this.executionStack.getAll();
        while (!executionStackCopy.isEmpty()) {
            ExecutionStackString.append(executionStackCopy.pop().toString()).append("\n");
        }
        StringBuilder SymbolTableString = new StringBuilder("SymbolTable:\n");
        for (String key : symbolTable.getAll()) {
            try {
                SymbolTableString.append(key).append(" -> ").append(symbolTable.get(key).toString()).append("\n");
            } catch (DictionaryException e) {
                continue;
            }
        }
        StringBuilder OutputString = new StringBuilder("Output:\n");
        for (ValueInterface value : out.getAll())
            OutputString.append(value.toString()).append("\n");
        StringBuilder FileTableString = new StringBuilder("FileTable:\n");
        for (StringValue key : fileTable.getAll()) {
            FileTableString.append(key.toString()).append("\n");
        }
        programStateString += ExecutionStackString.toString() + SymbolTableString.toString() +
                OutputString.toString() + FileTableString.toString() + heapTable.toString();
        return programStateString;
    }

    public ProgramState oneStep() throws StackException, DictionaryException,
            DivisionException, HeapException, TypeException, IOException, ExpressionException {
        if (executionStack.isEmpty()) {
            throw new StackException("Execution stack is empty!");
        }
        StatementInterface currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public void reset() {
        this.out.clear();
        this.executionStack.clear();
        this.symbolTable.clear();
        this.fileTable.clear();
        this.executionStack.push(originalProgram.deepCopy());
    }

    public Boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }
}
