package com.suciubogdan.demo2;

import com.suciubogdan.demo2.Controller.*;
import com.suciubogdan.demo2.Module.*;
import com.suciubogdan.demo2.Module.Containers.MyDictionary;
import com.suciubogdan.demo2.Module.Expression.*;
import com.suciubogdan.demo2.Module.Statement.*;
import com.suciubogdan.demo2.Module.Statement.StatementInterface;
import com.suciubogdan.demo2.Module.Type.*;
import com.suciubogdan.demo2.Module.Value.*;
import com.suciubogdan.demo2.Repository.MemoryRepository;
import com.suciubogdan.demo2.Repository.RepositoryInterface;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;


public class ListController {
    private ProgramController programController;

    public void setProgramController(ProgramController programController) {
        this.programController = programController;
    }

    @FXML
    private ListView<StatementInterface> statements;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize() {
        ArrayList<StatementInterface> examples = exampleList();
        statements.setItems(FXCollections.observableArrayList(examples));
        displayButton.setOnAction(actionEvent -> {
            int index = statements.getSelectionModel().getSelectedIndex();
            if (index < 0)
                return;
            StatementInterface statement = examples.get(index);
            try {
                statement.typeCheck(new MyDictionary<>());
                ProgramState programState = new ProgramState(statement);
                RepositoryInterface repository = new MemoryRepository(programState, "log11.txt");
                Service service = new Service(repository);
                programController.setController(service);
            } catch (Exception e) {
                System.out.println("Error in example 11: " + e.getMessage());
            }
//
//            ProgramState state = new ProgramState(examples.get(index));
//            RepositoryInterface repository = new MemoryRepository(state, "log%s.txt".formatted(index));
//            Service service = new Service(repository);
//
//            try{
//                controller.runTypeChecker();
//                programController.setController(controller);
//            } catch (ExpressionInterface interpreterError) {
//                Alert alert = new Alert(Alert.AlertType.ERROR, interpreterError.getMessage(), ButtonType.OK);
//                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
//                alert.showAndWait();
//            }
        });
    }


    private ArrayList<StatementInterface> exampleList(){
        ArrayList<StatementInterface> x = new ArrayList<>();
        StatementInterface example1 = new CompoundStatement(
                new VariableStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        StatementInterface example2 = new CompoundStatement(
                new VariableStatement("a", new IntType()),
                new CompoundStatement(
                        new VariableStatement("b", new IntType()),
                        new CompoundStatement(
                                new AssignStatement(
                                        "a",
                                        new ArithmeticExpression(
                                                "+",
                                                new ValueExpression(new IntValue(2)),
                                                new ArithmeticExpression(
                                                        "*",
                                                        new ValueExpression(new IntValue(3)),
                                                        new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(
                                        new AssignStatement(
                                                "b",
                                                new ArithmeticExpression(
                                                        '+',
                                                        new VariableExpression("a"),
                                                        new ValueExpression(new IntValue(1)))),
                                        new PrintStatement(new VariableExpression("b"))))));

        StatementInterface example3 = new CompoundStatement(
                new VariableStatement("a", new BoolType()),
                new CompoundStatement(
                        new VariableStatement("v", new IntType()),
                        new CompoundStatement(
                                new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignStatement(
                                                        "v",
                                                        new ValueExpression(new IntValue(2))),
                                                new AssignStatement(
                                                        "v",
                                                        new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));

        StatementInterface example4 = new CompoundStatement(
                new VariableStatement("varf", new StringType()),
                new CompoundStatement(
                        new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenReadStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableStatement("varc", new IntType()),
                                        new CompoundStatement(
                                                new ReadStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadStatement(new VariableExpression("varf"))))))))));

        StatementInterface example5 = new PrintStatement(new RelationalExpression(">", new ValueExpression(new IntValue(5)), new ValueExpression(new IntValue(6))));

        StatementInterface example6 = new CompoundStatement(
                new VariableStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new VariableStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                        new CompoundStatement(
                                new HeapAllocStatement("v", new ValueExpression(new IntValue(20))),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a")))),
                                                new CompoundStatement(
                                                        new HeapWriteStatement("v", new ValueExpression(new IntValue(30))),
                                                        new PrintStatement(new ArithmeticExpression("+", new HeapReadExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))))
                                                )
                                        )
                                )
                        )
                )
        );

        StatementInterface example7 = new CompoundStatement(
                new VariableStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignStatement("v", new ArithmeticExpression("-", new VariableExpression("v"), new ValueExpression(new IntValue(1))))
                                        )
                                ),
                                new PrintStatement(new ValueExpression(new StringValue("Done!")))
                        )
                )
        );

        StatementInterface example8 = new CompoundStatement(
                new VariableStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new VariableStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                        new CompoundStatement(
                                new HeapAllocStatement("v", new ValueExpression(new IntValue(20))),
                                new CompoundStatement(
                                        new NOPStatement(),
                                        new CompoundStatement(
                                                new NOPStatement(),
                                                new CompoundStatement(
                                                        new HeapAllocStatement("v", new ValueExpression(new IntValue(30))),
                                                        new PrintStatement(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a"))))
                                                )
                                        )
                                )
                        )
                )
        );

        StatementInterface example9 = new CompoundStatement(
                new VariableStatement("v", new IntType()),
                new CompoundStatement(
                        new VariableStatement("a", new ReferenceType(new IntType())),
                        new CompoundStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(new CompoundStatement(
                                                        new HeapWriteStatement("a", new ValueExpression(new IntValue(30))),
                                                        new CompoundStatement(
                                                                new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("v")),
                                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("a")))
                                                                )
                                                        )
                                                )),
                                                new CompoundStatement(
                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("a"))),
                                                        new CompoundStatement(
                                                                new NOPStatement(),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("v")),
                                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("a")))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        StatementInterface example10 = new CompoundStatement(
                new VariableStatement("v", new IntType()),
                new CompoundStatement(
                        new VariableStatement("a", new ReferenceType(new IntType())),
                        new CompoundStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(new CompoundStatement(
                                                        new HeapWriteStatement("a", new ValueExpression(new IntValue(30))),
                                                        new CompoundStatement(
                                                                new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("v")),
                                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("a")))
                                                                )
                                                        )
                                                )),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        StatementInterface example11 = new CompoundStatement(
                new VariableStatement("a", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new VariableStatement("counter", new IntType()),
                        new WhileStatement(
                                new RelationalExpression("<", new VariableExpression("counter"), new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new ForkStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new HeapAllocStatement("a", new VariableExpression("counter")),
                                                                new PrintStatement(new HeapReadExpression(new VariableExpression("a")))
                                                        )
                                                )
                                        ),
                                        new AssignStatement("counter", new ArithmeticExpression("+", new VariableExpression("counter"), new ValueExpression(new IntValue(1))))
                                )
                        )
                )
        );

        x.add(example1);
        x.add(example2);
        x.add(example3);
        x.add(example4);
        x.add(example5);
        x.add(example6);
        x.add(example7);
        x.add(example8);
        x.add(example9);
        x.add(example10);
        x.add(example11);
        return x;
    }
}
