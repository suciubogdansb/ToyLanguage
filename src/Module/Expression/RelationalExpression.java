package Module.Expression;

import Module.Containers.DictionaryInterface;
import Module.Exception.DictionaryException;
import Module.Exception.DivisionException;
import Module.Exception.ExpressionException;
import Module.Type.IntType;
import Module.Value.BoolValue;
import Module.Value.IntValue;
import Module.Value.ValueInterface;

public class RelationalExpression implements ExpressionInterface{
    ExpressionInterface left;
    ExpressionInterface right;
    int operator;

    public RelationalExpression(ExpressionInterface left, ExpressionInterface right, int operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public RelationalExpression(String sign, ExpressionInterface left, ExpressionInterface right) {
        this.left = left;
        this.right = right;
        switch (sign) {
            case "<" -> this.operator = 1;
            case "<=" -> this.operator = 2;
            case "==" -> this.operator = 3;
            case "!=" -> this.operator = 4;
            case ">" -> this.operator = 5;
            case ">=" -> this.operator = 6;
            default -> this.operator = -1;
        }
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> symbolTable) throws ExpressionException,
            DivisionException, DictionaryException {
        ValueInterface leftValue, rightValue;
        leftValue = left.evaluate(symbolTable);

        if (leftValue.getType().equals(new IntType())) {
            rightValue = right.evaluate(symbolTable);
            if (rightValue.getType().equals(new IntType())) {
                IntValue leftInt = (IntValue) leftValue;
                IntValue rightInt = (IntValue) rightValue;
                int leftNumber, rightNumber;
                leftNumber = leftInt.getValue();
                rightNumber = rightInt.getValue();
                return switch (operator) {
                    case 1 -> new BoolValue(leftNumber < rightNumber);
                    case 2 -> new BoolValue(leftNumber <= rightNumber);
                    case 3 -> new BoolValue(leftNumber == rightNumber);
                    case 4 -> new BoolValue(leftNumber != rightNumber);
                    case 5 -> new BoolValue(leftNumber > rightNumber);
                    case 6 -> new BoolValue(leftNumber >= rightNumber);
                    default -> throw new ExpressionException("Invalid operator");
                };
            } else {
                throw new ExpressionException("Second operand is not an integer");
            }
        } else {
            throw new ExpressionException("First operand is not an integer");
        }
    }

    @Override
    public String toString() {
        return switch (operator) {
            case 1 -> left.toString() + " < " + right.toString();
            case 2 -> left.toString() + " <= " + right.toString();
            case 3 -> left.toString() + " == " + right.toString();
            case 4 -> left.toString() + " != " + right.toString();
            case 5 -> left.toString() + " > " + right.toString();
            case 6 -> left.toString() + " >= " + right.toString();
            default -> "Invalid operator";
        };
    }

    @Override
    public ExpressionInterface deepCopy() {
        return new ArithmeticExpression(left.deepCopy(), right.deepCopy(), operator);
    }
}
