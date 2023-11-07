package Module.Value;

import Module.Type.IntType;
import Module.Type.TypeInterface;

public class IntValue implements ValueInterface{
    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public TypeInterface getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public ValueInterface deepCopy() {
        return new IntValue(value);
    }
}
