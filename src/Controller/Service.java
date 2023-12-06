package Controller;

import Module.Containers.StackInterface;
import Module.Exception.*;
import Module.Statement.NOPStatement;
import Module.Statement.StatementInterface;
import Module.Value.ReferenceValue;
import Module.Value.ValueInterface;
import Repository.MemoryRepository;
import Repository.RepositoryInterface;
import Module.ProgramState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Service {
    RepositoryInterface repository;

    public Service(RepositoryInterface repository) {
        this.repository = repository;
    }

    public Service() {
        this.repository = new MemoryRepository();
    }

    public void addProgram(String programString) {
        programString = programString.toLowerCase();
        List<String> statements = new ArrayList<>(Arrays.asList(programString.split("[^(].*\\s*;\\s*.*[^)]")));
        List<StatementInterface> statementList = new ArrayList<>();
        for (String statement : statements) {
            if (statement.isEmpty())
                statementList.add(new NOPStatement());
            //else if(statement.)
        }
    }

    Map<Integer, ValueInterface> unsafeGarbageCollector(List<Integer> symbolTableAddresses, Map<Integer, ValueInterface> heapTable) {
        return heapTable.entrySet().
                stream().
                filter(e -> symbolTableAddresses.contains(e.getKey())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Map<Integer, ValueInterface> safeGarbageCollector(List<Integer> symbolTableAddresses, Map<Integer, ValueInterface> heapTable) {
        return heapTable.entrySet().
                stream().
                filter(e -> symbolTableAddresses.contains(e.getKey()) || heapTable.entrySet().
                        stream().anyMatch(e1 -> { ValueInterface v = e1.getValue();
                            if(v instanceof ReferenceValue)
                                return ((ReferenceValue) v).getAddress() == e.getKey();
                            return false;
                        })).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddressesFromSymbolTable(List<ValueInterface> symbolTableValues) {
        return symbolTableValues.stream().
                filter(v -> v instanceof ReferenceValue).
                map(v -> ((ReferenceValue) v).getAddress()).
                collect(Collectors.toList());
    }


    public void addProgram(StatementInterface originalProgram) {
        repository.addProgram(new ProgramState(originalProgram));
    }

    public void runIndex(int index, boolean displayFlag) throws DivisionException, TypeException, StackException, ExpressionException,
            DictionaryException, IOException, RepositoryException, HeapException {
        repository.changeCurrentIndex(index);
        allStep();
    }

    public void allStep() throws DivisionException, TypeException, StackException, ExpressionException, DictionaryException, IOException, RepositoryException, HeapException {
        ProgramState programState = repository.getCurrentProgram();
        this.repository.logProgramStateExecution();
        while (!programState.getExecutionStack().isEmpty()) {
            oneStep(programState);
            this.repository.logProgramStateExecution();
            programState.getHeapTable().
                    setContent(
                            safeGarbageCollector(
                                    getAddressesFromSymbolTable(
                                            new ArrayList<>(programState.
                                                    getSymbolTable().
                                                    getContent().
                                                    values())
                                    ),
                                    programState.
                                            getHeapTable().
                                            getContent()
                            )
                    );
            this.repository.logProgramStateExecution();
        }
    }

    ProgramState oneStep(ProgramState programState) throws StackException,
            DivisionException, TypeException, ExpressionException, DictionaryException, IOException, HeapException {
        StackInterface<StatementInterface> stack = programState.getExecutionStack();
        if (stack.isEmpty()) {
            throw new StackException("Execution stack is empty!");
        }
        StatementInterface currentStatement = stack.pop();
        return currentStatement.execute(programState);
    }
}
