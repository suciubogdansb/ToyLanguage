package Module.Expression;

import Module.Containers.DictionaryInterface;
import Module.Exception.DictionaryException;
import Module.Exception.DivisionException;
import Module.Exception.ExpressionException;
import Module.Type.BoolType;
import Module.Type.IntType;
import Module.Value.BoolValue;
import Module.Value.IntValue;
import Module.Value.ValueInterface;

public class LogicalExpression implements ExpressionInterface{
    ExpressionInterface left;
    ExpressionInterface right;
    int operator;

    public LogicalExpression(ExpressionInterface left, ExpressionInterface right, int operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public LogicalExpression(String sign, ExpressionInterface left, ExpressionInterface right){
        this.left = left;
        this.right = right;
        switch (sign) {
            case "||" -> this.operator = 1;
            case "&&" -> this.operator = 2;
            default -> this.operator = -1;
        }
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> symbolTable) throws ExpressionException,
            DivisionException, DictionaryException {
        ValueInterface leftValue, rightValue;
        leftValue = left.evaluate(symbolTable);

        if(leftValue.getType().equals(new BoolType())){
            rightValue = right.evaluate(symbolTable);
            if(rightValue.getType().equals(new BoolType())){
                BoolValue leftBool = (BoolValue) leftValue;
                BoolValue rightBool = (BoolValue) rightValue;
                boolean leftBoolean, rightBoolean;
                leftBoolean = leftBool.getValue();
                rightBoolean = rightBool.getValue();
                return switch (operator) {
                    case 1 -> new BoolValue(leftBoolean || rightBoolean);
                    case 2 -> new BoolValue(leftBoolean && rightBoolean);
                    default -> throw new ExpressionException("Invalid operator");
                };
            }
            else{
                throw new ExpressionException("Second operand is not a boolean");
            }
        }
        else{
            throw new ExpressionException("First operand is not a boolean");
        }
    }
    @Override
    public String toString() {
        return switch (operator) {
            case 1 -> left.toString() + " || " + right.toString();
            case 2 -> left.toString() + " && " + right.toString();
            default -> "Invalid operator";
        };
    }

    @Override
    public ExpressionInterface deepCopy() {
        return new LogicalExpression(left.deepCopy(), right.deepCopy(), operator);
    }
}
