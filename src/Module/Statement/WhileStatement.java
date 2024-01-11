package Module.Statement;

import Module.Containers.DictionaryInterface;
import Module.Containers.StackInterface;
import Module.Exception.*;
import Module.Expression.ExpressionInterface;
import Module.ProgramState;
import Module.Type.BoolType;
import Module.Type.TypeInterface;
import Module.Value.BoolValue;
import Module.Value.ValueInterface;

public class WhileStatement implements StatementInterface {
    ExpressionInterface condition;
    StatementInterface statement;

    public WhileStatement(ExpressionInterface expression, StatementInterface statement) {
        this.condition = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws DivisionException, ExpressionException, DictionaryException, TypeException, IOException, HeapException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        ValueInterface conditionValue = condition.evaluate(symbolTable, state.getHeapTable());
        if (!conditionValue.getType().equals(new BoolType()))
            throw new TypeException("Condition is not a boolean");
        BoolValue boolValue = (BoolValue) conditionValue;
        if (boolValue.getValue()) {
            stack.push(this);
            stack.push(statement);
        }
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new WhileStatement(condition.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "( while (" + condition.toString() + ") do (" + statement.toString() + ") )";
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws ExpressionException, DictionaryException, TypeException {
        TypeInterface conditionType = condition.getType(typeTable);
        if (!conditionType.equals(new BoolType())) {
            throw new ExpressionException("While condition is not a boolean");
        }
        statement.typeCheck(typeTable.deepCopy());
        return typeTable;
    }
}
