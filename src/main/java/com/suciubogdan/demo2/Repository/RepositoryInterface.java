package com.suciubogdan.demo2.Repository;
import com.suciubogdan.demo2.Module.Exception.RepositoryException;
import com.suciubogdan.demo2.Module.ProgramState;

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
