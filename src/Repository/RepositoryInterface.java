package Repository;
import Module.Exception.RepositoryException;
import Module.ProgramState;

import java.util.List;

public interface RepositoryInterface {
    List<ProgramState> getProgramStates();

    void setProgramStates(List<ProgramState> programStates);

    ProgramState getCurrentNoReset();

    void changeCurrentIndex(int index);

    void addProgram(ProgramState programState);

    int getNumberOfPrograms();

    void logProgramStateExecution() throws RepositoryException;

    void logProgramStateExecution(ProgramState programState) throws RepositoryException;
}
