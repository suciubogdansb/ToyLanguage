package Controller;

import Module.Containers.StackInterface;
import Module.Exception.*;
import Module.Statement.NOPStatement;
import Module.Statement.StatementInterface;
import Repository.MemoryRepository;
import Repository.RepositoryInterface;
import Module.ProgramState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            if(statement.isEmpty())
                statementList.add(new NOPStatement());
            //else if(statement.)
        }
    }

    public void addProgram(StatementInterface originalProgram) {
        repository.addProgram(new ProgramState(originalProgram));
    }

    public void runIndex(int index) throws DivisionException, TypeException, StackException, ExpressionException,
            DictionaryException {
        repository.changeCurrentIndex(index);
        allStep();
    }

    void allStep() throws DivisionException, TypeException, StackException, ExpressionException, DictionaryException {
        ProgramState programState = repository.getCurrentProgram();
        System.out.println(programState);
        while (!programState.getExecutionStack().isEmpty()) {
            oneStep(programState);
            System.out.println(programState);
        }
    }

    ProgramState oneStep(ProgramState programState) throws StackException,
            DivisionException, TypeException, ExpressionException, DictionaryException {
        StackInterface<StatementInterface> stack = programState.getExecutionStack();
        if(stack.isEmpty()) {
            throw new StackException("Execution stack is empty!");
        }
        StatementInterface currentStatement = stack.pop();
        return currentStatement.execute(programState);
    }
}
