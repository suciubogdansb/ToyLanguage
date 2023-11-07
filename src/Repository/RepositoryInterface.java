package Repository;
import Module.ProgramState;

public interface RepositoryInterface {
    ProgramState getCurrentProgram();

    void changeCurrentIndex(int index);

    void addProgram(ProgramState programState);

    int getNumberOfPrograms();
}
