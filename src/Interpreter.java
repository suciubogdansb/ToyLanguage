import Controller.Service;
import Module.Expression.*;
import Module.Statement.*;
import Module.Type.BoolType;
import Module.Type.IntType;
import Module.Type.ReferenceType;
import Module.Type.StringType;
import Module.Value.BoolValue;
import Module.Value.IntValue;
import Module.Value.StringValue;
import Repository.RepositoryInterface;
import Module.ProgramState;
import View.Commands.ExitCommand;
import View.Commands.RunExample;
import View.OldUI;
import View.TextMenu;

public class Interpreter {
    public static void main(String[] args) {
        StatementInterface example1 = new CompoundStatement(
                new VariableStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        ProgramState program1 = new ProgramState(example1);
        RepositoryInterface repo1 = new Repository.MemoryRepository(program1, "log1.txt");
        Service service1 = new Service(repo1);

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
        ProgramState program2 = new ProgramState(example2);
        RepositoryInterface repo2 = new Repository.MemoryRepository(program2, "log2.txt");
        Service service2 = new Service(repo2);

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
        ProgramState program3 = new ProgramState(example3);
        RepositoryInterface repo3 = new Repository.MemoryRepository(program3, "log3.txt");
        Service service3 = new Service(repo3);

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
        ProgramState program4 = new ProgramState(example4);
        RepositoryInterface repo4 = new Repository.MemoryRepository(program4, "log4.txt");
        Service service4 = new Service(repo4);

        StatementInterface example5 = new PrintStatement(new RelationalExpression(">", new ValueExpression(new IntValue(5)), new ValueExpression(new IntValue(6))));
        ProgramState program5 = new ProgramState(example5);
        RepositoryInterface repo5 = new Repository.MemoryRepository(program5, "log5.txt");
        Service service5 = new Service(repo5);

        StatementInterface example6 = new CompoundStatement(
                new VariableStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new VariableStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                        new CompoundStatement(
                                new HeapAllocStatement("v", new ValueExpression(new IntValue(20))),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new HeapReadExpression(new HeapReadExpression(new ValueExpression(new StringValue("a"))))),
                                                new CompoundStatement(
                                                        new HeapWriteStatement("v", new ValueExpression(new IntValue(30))),
                                                        new PrintStatement(new ArithmeticExpression("+", new HeapReadExpression(new ValueExpression(new StringValue("v"))), new ValueExpression(new IntValue(5))))
                                                )
                                        )
                                )
                        )
                )
        );
        ProgramState program6 = new ProgramState(example6);
        RepositoryInterface repo6 = new Repository.MemoryRepository(program6, "log6.txt");
        Service service6 = new Service(repo6);

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
        ProgramState program7 = new ProgramState(example7);
        RepositoryInterface repo7 = new Repository.MemoryRepository(program7, "log7.txt");
        Service service7 = new Service(repo7);

        StatementInterface example8 = new CompoundStatement(
                new VariableStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new VariableStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                        new CompoundStatement(
                                new HeapAllocStatement("v", new ValueExpression(new IntValue(20))),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new HeapReadExpression(new HeapReadExpression(new ValueExpression(new StringValue("a"))))),
                                                new CompoundStatement(
                                                        new HeapAllocStatement("v", new ValueExpression(new IntValue(30))),
                                                        new PrintStatement(new HeapReadExpression(new HeapReadExpression(new ValueExpression(new StringValue("a")))))
                                                )
                                        )
                                )
                        )
                )
        );
        ProgramState program8 = new ProgramState(example8);
        RepositoryInterface repo8 = new Repository.MemoryRepository(program8, "log8.txt");
        Service service8 = new Service(repo8);

        TextMenu menu = new TextMenu();
        menu.add(new ExitCommand("0", "exit"));
        menu.add(new RunExample("1", example1.toString(), service1));
        menu.add(new RunExample("2", example2.toString(), service2));
        menu.add(new RunExample("3", example3.toString(), service3));
        menu.add(new RunExample("4", example4.toString(), service4));
        menu.add(new RunExample("5", example5.toString(), service5));
        menu.add(new RunExample("6", example6.toString(), service6));
        menu.add(new RunExample("7", example7.toString(), service7));
        menu.add(new RunExample("8", example8.toString(), service8));
        menu.show();
    }
}
