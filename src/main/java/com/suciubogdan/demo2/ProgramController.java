package com.suciubogdan.demo2;

import com.suciubogdan.demo2.Controller.Service;
import com.suciubogdan.demo2.Module.Containers.HeapInterface;
import com.suciubogdan.demo2.Module.Containers.HeapTable;
import com.suciubogdan.demo2.Module.Containers.ListInterface;
import com.suciubogdan.demo2.Module.Containers.MyList;
import com.suciubogdan.demo2.Module.Exception.*;
import com.suciubogdan.demo2.Module.ProgramState;
import com.suciubogdan.demo2.Module.Statement.StatementInterface;
import com.suciubogdan.demo2.Module.Value.StringValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.*;
import java.util.stream.Collectors;

class Pair<T1, T2> {
    T1 first;
    T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
}

public class ProgramController {
    private Service controller;

    @FXML
    private TableView<Pair<Integer, ValueInterface>> heapTable;

    @FXML
    private TableColumn<Pair<Integer, ValueInterface>, Integer> addressColumn;

    @FXML
    private TableColumn<Pair<Integer, ValueInterface>, String> valueColumn;

    @FXML
    private ListView<String> outputList;

    @FXML
    private ListView<String> fileList;

    @FXML
    private ListView<Integer> programStateList;

    @FXML
    private ListView<String> executionStackList;

    @FXML
    private TableView<Pair<String, ValueInterface>> symbolTable;

    @FXML
    private TableColumn<Pair<String, ValueInterface>, String> symVariableColumn;

    @FXML
    private TableColumn<Pair<String, ValueInterface>, String> symValueColumn;

    @FXML
    private TextField numberOfProgramStates;

    @FXML
    private Button oneStep;

    @FXML
    public void initialize() {
        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().first).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        symVariableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().first));
        symValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        oneStep.setOnAction(actionEvent -> {
            if(controller == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            boolean programStateLeft = Objects.requireNonNull(getCurrentProgramState()).getExecutionStack().isEmpty();
            if(programStateLeft){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            try {
                controller.oneStepForGUI();
                populate();
            } catch (ServiceException interpreterError) {
                Alert alert = new Alert(Alert.AlertType.ERROR, interpreterError.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
        programStateList.setOnMouseClicked(mouseEvent -> populate());
    }

    private ProgramState getCurrentProgramState(){
        if (controller.getProgramStates().isEmpty())
            return null;
        int currentId = programStateList.getSelectionModel().getSelectedIndex();
        if (currentId == -1)
            return controller.getProgramStates().get(0);
        return controller.getProgramStates().get(currentId);
    }

    public void setController(Service controller) {
        this.controller = controller;
        populate();
    }

    private void populate() {
        populateHeap();
        populateOutput();
        populateProgramStateIdentifiers();
        populateFileTable();
        populateSymbolTable();
        populateExecutionStack();
    }

    private void populateHeap() {
        HeapInterface heap;
        if (!controller.getProgramStates().isEmpty())
            heap = controller.getProgramStates().get(0).getHeapTable();
        else heap = new HeapTable();
        List<Pair<Integer, ValueInterface>> heapTableList = new ArrayList<>();
        for (Object key : heap.getContent().keySet()) {
            Integer keyInt = (Integer) key;
            heapTableList.add(new Pair<>(keyInt, (ValueInterface) heap.getContent().get(key)));
        }
        heapTable.setItems(FXCollections.observableList(heapTableList));
        heapTable.refresh();
    }

    private void populateProgramStateIdentifiers() {
        List<ProgramState> programStates = controller.getProgramStates();
        List<Integer> idList = new ArrayList<>();
        if (!(programStates.size() == 1 && !programStates.get(0).isNotCompleted())) {
            idList = programStates.stream().map(ProgramState::getId).collect(Collectors.toList());
        }
        else{
            idList.add(programStates.get(0).getId());
        }
        programStateList.setItems(FXCollections.observableList(idList));
        numberOfProgramStates.setText("" + programStates.size());
    }

    private void populateFileTable() {
        ArrayList<String> files;
        if (!controller.getProgramStates().isEmpty())
            files = controller.getProgramStates().get(0).getFileTable().getKeys().stream().map(StringValue::getValue).collect(Collectors.toCollection(ArrayList::new));
        else files = new ArrayList<>();
        fileList.setItems(FXCollections.observableArrayList(files));
    }

    private void populateOutput() {
        ListInterface<ValueInterface> output;
        if (!controller.getProgramStates().isEmpty())
            output = controller.getProgramStates().get(0).getOutput();
        else output = new MyList<>();
        outputList.setItems(FXCollections.observableList(output.getAll().stream().map(ValueInterface::toString).collect(Collectors.toList())));
        outputList.refresh();
    }

    private void populateSymbolTable() {
        ProgramState state = getCurrentProgramState();
        List<Pair<String, ValueInterface>> symbolTableList = new ArrayList<>();
        if (state != null)
            for (Map.Entry<String, ValueInterface> entry : state.getSymbolTable().getContent().entrySet())
                symbolTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        symbolTable.setItems(FXCollections.observableList(symbolTableList));
        symbolTable.refresh();
    }

    private void populateExecutionStack() {
        ProgramState state = getCurrentProgramState();
        List<String> executionStackListAsString = new ArrayList<>();
        if (state != null)
            for(StatementInterface s : state.getExecutionStack().getAll()){
                executionStackListAsString.add(s.toString());
            }
        Collections.reverse(executionStackListAsString);
        executionStackList.setItems(FXCollections.observableList(executionStackListAsString));
        executionStackList.refresh();
    }
}
