package Repository;
import Module.ProgramState;

import java.util.ArrayList;
import java.util.List;

public class MemoryRepository implements RepositoryInterface{
    private List<ProgramState> programStates;
    int current;

    public MemoryRepository(List<ProgramState> programStates) {
        this.programStates = programStates;
        this.current = 0;
    }

    public MemoryRepository() {
        this.programStates = new ArrayList<ProgramState>();
        this.current = 0;
    }

    @Override
    public ProgramState getCurrentProgram() {
        if(programStates.get(current).getExecutionStack().isEmpty())
            programStates.get(current).reset();
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
}
