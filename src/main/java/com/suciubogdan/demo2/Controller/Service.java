package com.suciubogdan.demo2.Controller;

import com.suciubogdan.demo2.Module.Containers.DictionaryInterface;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Value.ReferenceValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;
import com.suciubogdan.demo2.Repository.MemoryRepository;
import com.suciubogdan.demo2.Repository.RepositoryInterface;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Service {
    RepositoryInterface repository;

    ExecutorService executor;

    public Service(RepositoryInterface repository) {
        this.repository = repository;
    }

    public Service() {
        this.repository = new MemoryRepository();
    }

//    public void addProgram(String programString) {
//        programString = programString.toLowerCase();
//        List<String> statements = new ArrayList<>(Arrays.asList(programString.split("[^(].*\\s*;\\s*.*[^)]")));
//        List<StatementInterface> statementList = new ArrayList<>();
//        for (String statement : statements) {
//            if (statement.isEmpty())
//                statementList.add(new NOPStatement());
//            //else if(statement.)
//        }
//    }

    public List<ProgramState> getProgramStates() {
        return repository.getProgramStates();}
    Map<Integer, ValueInterface> garbageCollector(Set<Integer> symbolTableAddresses, Map<Integer, ValueInterface> heapTable) {
        return heapTable.entrySet().
                stream().
                filter(e -> symbolTableAddresses.contains(e.getKey())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Set<Integer> getAddressesFromSymbolTable(List<Collection<ValueInterface>> symbolTableValues, Map<Integer, ValueInterface> heapTable) {
        return Stream.concat(symbolTableValues.stream().
                flatMap(Collection::stream).
                filter(v -> v instanceof ReferenceValue).
                map(v -> ((ReferenceValue) v).getAddress()),
                heapTable.values().
                stream().
                filter(valueInterface -> valueInterface instanceof ReferenceValue).
                map(valueInterface -> ((ReferenceValue) valueInterface).getAddress())
                ).collect(Collectors.toSet());
    }


//    public void addProgram(StatementInterface originalProgram) {
//        repository.addProgram(new ProgramState(originalProgram));
//    }
//
//    public void runIndex(int index, boolean displayFlag) throws DivisionException, TypeException, StackException, ExpressionException,
//            DictionaryException, IOException, RepositoryException, HeapException {
//        repository.changeCurrentIndex(index);
//        allStep();
//    }

//    public void allStep() throws DivisionException, TypeException, StackException, ExpressionException, DictionaryException, IOException, RepositoryException, HeapException {
//        ProgramState programState = repository.getCurrentProgram();
//        this.repository.logProgramStateExecution();
//        while (!programState.getExecutionStack().isEmpty()) {
//            oneStep(programState);
//            this.repository.logProgramStateExecution();
//            programState.getHeapTable().
//                    setContent(
//                            safeGarbageCollector(
//                                    getAddressesFromSymbolTable(
//                                            new ArrayList<>(programState.
//                                                    getSymbolTable().
//                                                    getContent().
//                                                    values())
//                                    ),
//                                    programState.
//                                            getHeapTable().
//                                            getContent()
//                            )
//                    );
//            this.repository.logProgramStateExecution();
//        }
//    }

//    ProgramState oneStep(ProgramState programState) throws StackException,
//            DivisionException, TypeException, ExpressionException, DictionaryException, IOException, HeapException {
//        StackInterface<StatementInterface> stack = programState.getExecutionStack();
//        if (stack.isEmpty()) {
//            throw new StackException("Execution stack is empty!");
//        }
//        StatementInterface currentStatement = stack.pop();
//        return currentStatement.execute(programState);
//    }

    void oneStepAllPrograms(List<ProgramState> programList) throws ServiceException {
//        logProgramStates(programList);
        List<Callable<ProgramState>> callList = programList.stream()
                .map((ProgramState program) -> (Callable<ProgramState>)(program::oneStep))
                .toList();
        List<Object> newProgramList;
        try {
            newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException | InterruptedException e) {
                            return e.getCause();
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        }
        catch (InterruptedException e){
            throw new ServiceException(e.getMessage());
        }
        List<ProgramState> newActualPrograms = newProgramList.stream()
                .filter(p -> p instanceof ProgramState)
                .map(p -> (ProgramState) p).toList();

        List<Throwable> exceptionsList = newProgramList.stream()
                .filter(e -> !(e instanceof ProgramState))
                .map(e -> (Throwable) e).toList();

        for(Throwable t: exceptionsList){
            if(t instanceof Exception)
                throw new ServiceException(t.getMessage());
            else{
                throw new RuntimeException(t.getMessage());
            }
        }
        programList.addAll(newActualPrograms);
//        logProgramStates(programList);
        repository.setProgramStates(programList);
    }

    public void oneStepForGUI() throws ServiceException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedPrograms(repository.getProgramStates());
        logProgramStates(programStateList);
        ProgramState programState = programStateList.get(0);
        programState.getHeapTable().setContent(
                garbageCollector(
                        getAddressesFromSymbolTable(
                                programStateList.
                                        stream().
                                        map(ProgramState::getSymbolTable).
                                        map(DictionaryInterface::getContent).
                                        map(Map::values).
                                        toList(),
                                programState.
                                        getHeapTable().
                                        getContent()
                        ),
                        programState.
                                getHeapTable().
                                getContent()
                )
        );
        oneStepAllPrograms(repository.getProgramStates());
        repository.setProgramStates(removeCompletedPrograms(repository.getProgramStates()));
        logProgramStates(repository.getProgramStates());
        executor.shutdownNow();
    }

    void logProgramStates(List<ProgramState> programStateList){
        programStateList.forEach(x -> {
            try {
                repository.logProgramStateExecution(x);
            } catch (RepositoryException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        });
    }

    public void allStep() throws ServiceException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedPrograms(repository.getProgramStates());
        logProgramStates(programStateList);
        while(!programStateList.isEmpty()){
            oneStepAllPrograms(programStateList);
            programStateList.get(0).getHeapTable().setContent(
                    garbageCollector(
                            getAddressesFromSymbolTable(
                                    programStateList.
                                            stream().
                                            map(ProgramState::getSymbolTable).
                                            map(DictionaryInterface::getContent).
                                            map(Map::values).
                                            toList(),
                                    programStateList.
                                            get(0).
                                            getHeapTable().
                                            getContent()
                            ),
                            programStateList.
                                    get(0).
                                    getHeapTable().
                                    getContent()
                    )
            );
            logProgramStates(programStateList);
            programStateList = removeCompletedPrograms(repository.getProgramStates());
        }
        executor.shutdownNow();
        repository.setProgramStates(programStateList);
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStateList) {
        if (programStateList.size() == 1 && !programStateList.get(0).isNotCompleted())
            return programStateList;
        return programStateList.stream().
                filter(ProgramState::isNotCompleted).
                collect(Collectors.toList());
    }


}
