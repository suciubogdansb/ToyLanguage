package Repository;
import Module.Exception.RepositoryException;
import Module.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MemoryRepository implements RepositoryInterface{
    private List<ProgramState> programStates;
    int current;
    String logFilePath;

    public MemoryRepository(List<ProgramState> programStates) {
        this.programStates = programStates;
        this.current = 0;
        this.logFilePath = "logFile.txt";
    }

    public MemoryRepository() {
        this.programStates = new ArrayList<ProgramState>();
        this.current = 0;
        this.logFilePath = "logFile.txt";
    }

    public MemoryRepository(ProgramState programState, String logFilePath){
        this.programStates = new ArrayList<ProgramState>();
        this.programStates.add(programState);
        this.current = 0;
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return programStates;
    }
    @Override
    public void setProgramStates(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public ProgramState getCurrentNoReset(){
        return programStates.get(current);
    }

    @Override
    public void changeCurrentIndex(int index) {
        this.current = index;
    }

    @Override
    public void addProgram(ProgramState programState) {
        programStates.add(programState);
        this.current = programStates.size() - 1;
    }

    @Override
    public int getNumberOfPrograms() {
        return programStates.size();
    }

    @Override
    public void logProgramStateExecution() throws RepositoryException{
        try(PrintWriter fileDescriptor = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));){
            fileDescriptor.println(this.getCurrentNoReset().toString());
        }
        catch (Exception e){
            throw new RepositoryException(e.getMessage());
        }
    }
    @Override
    public void logProgramStateExecution(ProgramState programState) throws RepositoryException{
        try(PrintWriter fileDescriptor = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));){
            fileDescriptor.println(programState.toString());
        }
        catch (Exception e){
            throw new RepositoryException(e.getMessage());
        }
    }
}
