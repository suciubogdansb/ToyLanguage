package Module.Value;

import Module.Type.BoolType;
import Module.Type.TypeInterface;

public class BoolValue implements ValueInterface{
    private boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public TypeInterface getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public ValueInterface deepCopy() {
        return new BoolValue(value);
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof BoolValue) {
            return this.value == ((BoolValue) another).getValue();
        }
        return false;
    }
}
