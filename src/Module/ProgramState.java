package Module;

import Module.Containers.*;
import Module.Statement.StatementInterface;
import Module.Value.ValueInterface;
import com.sun.jdi.Value;

public class ProgramState {
    ListInterface<ValueInterface> out;
    StackInterface<StatementInterface> executionStack;
    DictionaryInterface<String, ValueInterface> symbolTable;
    StatementInterface originalProgram;

    ProgramState(ListInterface<ValueInterface> out, StackInterface<StatementInterface> executionStack,
                 DictionaryInterface<String, ValueInterface> symbolTable, StatementInterface originalProgram) {
        this.out = out;
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.originalProgram = originalProgram.deepCopy();
        this.executionStack.push(originalProgram);
    }

    public ProgramState(StatementInterface originalProgram){
        this.out = new MyList<ValueInterface>();
        this.executionStack = new MyStack<StatementInterface>();
        this.symbolTable = new MyDictionary<String, ValueInterface>();
        this.originalProgram = originalProgram.deepCopy();
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

    public void setExecutionStack(StackInterface<StatementInterface> executionStack) {
        this.executionStack = executionStack;
    }

    public void setSymbolTable(DictionaryInterface<String, ValueInterface> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void setOutput(ListInterface<ValueInterface> out) {
        this.out = out;
    }

    @Override
    public String toString() {
        return "ProgramState(" +
                "OutputList=" + out +
                ", ExecutionStack=" + executionStack +
                ", SymbolTable=" + symbolTable +
                ')';
    }

    public void reset() {
        this.out.clear();
        this.executionStack.clear();
        this.symbolTable.clear();
        this.executionStack.push(originalProgram.deepCopy());
    }
}
