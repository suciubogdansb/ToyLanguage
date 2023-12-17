//package View;
//
//import Controller.Service;
//import Module.Exception.*;
//import Module.Expression.ArithmeticExpression;
//import Module.Expression.ValueExpression;
//import Module.Expression.VariableExpression;
//import Module.Statement.*;
//import Module.Type.BoolType;
//import Module.Type.IntType;
//import Module.Value.BoolValue;
//import Module.Value.IntValue;
//
//import java.util.Scanner;
//
//public class OldUI {
//    Service service;
//
//    public OldUI(Service service) {
//        this.service = service;
//        this.addHardcodedPrograms();
//        this.menu();
//    }
//
//    public OldUI(){
//        this.service = new Service();
//        this.addHardcodedPrograms();
//        this.menu();
//    }
//
//    private void addHardcodedPrograms() {
//        //int v; v=2; Print(v)
//        StatementInterface program1 = new CompoundStatement(
//                new VariableStatement("v", new IntType()),
//                new CompoundStatement(
//                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
//                        new PrintStatement(new VariableExpression("v"))));
//        this.service.addProgram(program1);
//        //int a; int b; a=2+3*5; b=a+1; Print(b)
//        StatementInterface program2 = new CompoundStatement(
//                new VariableStatement("a", new IntType()),
//                new CompoundStatement(
//                        new VariableStatement("b", new IntType()),
//                        new CompoundStatement(
//                                new AssignStatement(
//                                    "a",
//                                    new ArithmeticExpression(
//                                    "+",
//                                    new ValueExpression(new IntValue(2)),
//                                    new ArithmeticExpression(
//                                        "*",
//                                        new ValueExpression(new IntValue(3)),
//                                        new ValueExpression(new IntValue(5))))),
//                                new CompoundStatement(
//                                    new AssignStatement(
//                                        "b",
//                                        new ArithmeticExpression(
//                                            '+',
//                                            new VariableExpression("a"),
//                                            new ValueExpression(new IntValue(1)))),
//                                    new PrintStatement(new VariableExpression("b"))))));
//        this.service.addProgram(program2);
//        // a = true; v = 2; if (a) then v = 2 else v = 3; print(v)
//        StatementInterface program3 = new CompoundStatement(
//                new VariableStatement("a", new BoolType()),
//                new CompoundStatement(
//                        new VariableStatement("v", new IntType()),
//                        new CompoundStatement(
//                                new AssignStatement("a", new ValueExpression(new BoolValue(true))),
//                                new CompoundStatement(
//                                    new IfStatement(
//                                        new VariableExpression("a"),
//                                        new AssignStatement(
//                                                "v",
//                                                new ValueExpression(new IntValue(2))),
//                                        new AssignStatement(
//                                                "v",
//                                                new ValueExpression(new IntValue(3)))),
//                                    new PrintStatement(new VariableExpression("v"))))));
//        this.service.addProgram(program3);
//    }
//
//    void menu(){
//        boolean displayFlag = true;
//        Scanner scanner = new Scanner(System.in);
//        boolean isRunning = true;
//        while(isRunning){
//            System.out.println("""
//Menu:
//1. Run program 1
//2. Run program 2
//3. Run program 3
//4. Switch flag. Current flag:\s""" + displayFlag + "\n0. Exit");
//            try{
//                String input = scanner.nextLine();
//                switch (input){
//                    case "1":
//                        this.service.runIndex(0, displayFlag);
//                        break;
//                    case "2":
//                        this.service.runIndex(1, displayFlag);
//                        break;
//                    case "3":
//                        this.service.runIndex(2, displayFlag);
//                        break;
//                    case "4":
//                        displayFlag = !displayFlag;
//                        break;
//                    case "0":
//                        isRunning = false;
//                        break;
//                    default:
//                        throw new IOException("Wrong Input");
//                }
//            }
//            catch (IOException | DictionaryException | DivisionException | ExpressionException | StackException |
//                   TypeException e){
//                System.out.println(e.getClass() + ": " + e.getMessage());
//            }
//            catch (Exception e){
//                System.out.println("Exception: " + e.getMessage());
//            }
//        }
//        scanner.close();
//        System.out.println("Bye!");
//    }
//}
