import Controller.Service;
import Module.Containers.MyDictionary;
import Module.Expression.*;
import Module.Statement.*;
import Module.Type.*;
import Module.Value.BoolValue;
import Module.Value.IntValue;
import Module.Value.StringValue;
import Repository.RepositoryInterface;
import Module.ProgramState;
import View.Commands.ExitCommand;
import View.Commands.RunExample;
//import View.OldUI;
import View.TextMenu;

public class Interpreter {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.add(new ExitCommand("0", "exit"));

        StatementInterface example1 = new CompoundStatement(
                new VariableStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        try {
            example1.typeCheck(new MyDictionary<>());
            ProgramState program1 = new ProgramState(example1);
            RepositoryInterface repo1 = new Repository.MemoryRepository(program1, "log1.txt");
            Service service1 = new Service(repo1);
            menu.add(new RunExample("1", example1.toString(), service1));
        } catch (Exception e) {
            System.out.println("Error in example 1: " + e.getMessage());
        }

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
//        ProgramState program2 = new ProgramState(example2);
//        RepositoryInterface repo2 = new Repository.MemoryRepository(program2, "log2.txt");
//        Service service2 = new Service(repo2);
        try {
            example2.typeCheck(new MyDictionary<>());
            ProgramState program2 = new ProgramState(example2);
            RepositoryInterface repo2 = new Repository.MemoryRepository(program2, "log2.txt");
            Service service2 = new Service(repo2);
            menu.add(new RunExample("2", example1.toString(), service2));
        } catch (Exception e) {
            System.out.println("Error in example 2: " + e.getMessage());
        }

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

        try {
            example3.typeCheck(new MyDictionary<>());
            ProgramState program3 = new ProgramState(example3);
            RepositoryInterface repo3 = new Repository.MemoryRepository(program3, "log3.txt");
            Service service3 = new Service(repo3);
            menu.add(new RunExample("3", example3.toString(), service3));
        } catch (Exception e) {
            System.out.println("Error in example 3: " + e.getMessage());
        }

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
        try {
            example4.typeCheck(new MyDictionary<>());
            ProgramState program4 = new ProgramState(example4);
            RepositoryInterface repo4 = new Repository.MemoryRepository(program4, "log4.txt");
            Service service4 = new Service(repo4);
            menu.add(new RunExample("4", example4.toString(), service4));
        } catch (Exception e) {
            System.out.println("Error in example 4: " + e.getMessage());
        }

        StatementInterface example5 = new PrintStatement(new RelationalExpression(">", new ValueExpression(new IntValue(5)), new ValueExpression(new IntValue(6))));
        try {
            example5.typeCheck(new MyDictionary<>());
            ProgramState program5 = new ProgramState(example5);
            RepositoryInterface repo5 = new Repository.MemoryRepository(program5, "log5.txt");
            Service service5 = new Service(repo5);
            menu.add(new RunExample("5", example5.toString(), service5));
        } catch (Exception e) {
            System.out.println("Error in example 5: " + e.getMessage());
        }

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

        try {
            example6.typeCheck(new MyDictionary<>());
            ProgramState program6 = new ProgramState(example6);
            RepositoryInterface repo6 = new Repository.MemoryRepository(program6, "log6.txt");
            Service service6 = new Service(repo6);
            menu.add(new RunExample("6", example6.toString(), service6));
        } catch (Exception e) {
            System.out.println("Error in example 6: " + e.getMessage());
        }

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
        try {
            example7.typeCheck(new MyDictionary<>());
            ProgramState program7 = new ProgramState(example7);
            RepositoryInterface repo7 = new Repository.MemoryRepository(program7, "log7.txt");
            Service service7 = new Service(repo7);
            menu.add(new RunExample("7", example7.toString(), service7));
        } catch (Exception e) {
            System.out.println("Error in example 7: " + e.getMessage());
        }

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
        try {
            example8.typeCheck(new MyDictionary<>());
            ProgramState program8 = new ProgramState(example8);
            RepositoryInterface repo8 = new Repository.MemoryRepository(program8, "log8.txt");
            Service service8 = new Service(repo8);
            menu.add(new RunExample("8", example8.toString(), service8));
        } catch (Exception e) {
            System.out.println("Error in example 8: " + e.getMessage());
        }

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
        try {
            example9.typeCheck(new MyDictionary<>());
            ProgramState program9 = new ProgramState(example9);
            RepositoryInterface repo9 = new Repository.MemoryRepository(program9, "log9.txt");
            Service service9 = new Service(repo9);
            menu.add(new RunExample("9", example9.toString(), service9));
        } catch (Exception e) {
            System.out.println("Error in example 9: " + e.getMessage());
        }

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

        try {
            example10.typeCheck(new MyDictionary<>());
            ProgramState program10 = new ProgramState(example10);
            RepositoryInterface repo10 = new Repository.MemoryRepository(program10, "log10.txt");
            Service service10 = new Service(repo10);
            menu.add(new RunExample("10", example10.toString(), service10));
        } catch (Exception e) {
            System.out.println("Error in example 10: " + e.getMessage());
        }

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

        try {
            example11.typeCheck(new MyDictionary<>());
            ProgramState program11 = new ProgramState(example11);
            RepositoryInterface repo11 = new Repository.MemoryRepository(program11, "log11.txt");
            Service service11 = new Service(repo11);
            menu.add(new RunExample("11", example11.toString(), service11));
        } catch (Exception e) {
            System.out.println("Error in example 11: " + e.getMessage());
        }


        menu.show();
    }
}
