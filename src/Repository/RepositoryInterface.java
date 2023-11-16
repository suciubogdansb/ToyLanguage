package Repository;
import Module.Exception.RepositoryException;
import Module.ProgramState;

public interface RepositoryInterface {
    ProgramState getCurrentProgram();

    void changeCurrentIndex(int index);

    void addProgram(ProgramState programState);

    int getNumberOfPrograms();

    void logProgramStateExecution() throws RepositoryException;
}
