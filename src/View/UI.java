package View;

import Controller.Service;
import Module.Exception.*;
import Module.Expression.ArithmeticExpression;
import Module.Expression.ValueExpression;
import Module.Expression.VariableExpression;
import Module.Statement.*;
import Module.Type.BoolType;
import Module.Type.IntType;
import Module.Value.BoolValue;
import Module.Value.IntValue;

import java.util.Scanner;

public class UI {
    Service service;

    public UI(Service service) {
        this.service = service;
        this.addHardcodedPrograms();
        this.menu();
    }

    public UI(){
        this.service = new Service();
        this.addHardcodedPrograms();
        this.menu();
    }

    private void addHardcodedPrograms() {
        StatementInterface program1 = new CompoundStatement(
                new VariableStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        this.service.addProgram(program1);
        StatementInterface program2 = new CompoundStatement(
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
        this.service.addProgram(program2);
        StatementInterface program3 = new CompoundStatement(
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
        this.service.addProgram(program3);
    }

    void menu(){
        boolean displayFlag = true;
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while(isRunning){
            System.out.println("""
Menu:
1. Run program 1
2. Run program 2
3. Run program 3
4. Switch flag. Current flag:\s""" + displayFlag + "\n0. Exit");
            try{
                String input = scanner.nextLine();
                switch (input){
                    case "1":
                        this.service.runIndex(0, displayFlag);
                        break;
                    case "2":
                        this.service.runIndex(1, displayFlag);
                        break;
                    case "3":
                        this.service.runIndex(2, displayFlag);
                        break;
                    case "4":
                        displayFlag = !displayFlag;
                        break;
                    case "0":
                        isRunning = false;
                        break;
                    default:
                        throw new IOException("Wrong Input");
                }
            }
            catch (IOException e){
                System.out.println(e.getClass() + ": " + e.getMessage());
            }
            catch (DictionaryException e) {
                System.out.println(e.getClass() + ": " + e.getMessage());
            }
            catch (DivisionException e) {
                System.out.println(e.getClass() + ": " + e.getMessage());
            }
            catch (ExpressionException e){
                System.out.println(e.getClass() + ": " + e.getMessage());
            }
            catch (StackException e){
                System.out.println(e.getClass() + ": " + e.getMessage());
            }
            catch (TypeException e){
                System.out.println(e.getClass() + ": " + e.getMessage());
            }
            catch (Exception e){
                System.out.println("Exception: " + e.getMessage());
            }
        }
        scanner.close();
        System.out.println("Bye!");
    }
}
