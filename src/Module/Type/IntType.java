package Module.Type;

import Module.Value.IntValue;
import Module.Value.ValueInterface;

public class IntType implements TypeInterface{
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    public String toString() {
        return "int";
    }

    @Override
    public ValueInterface defaultValue() {
        return new IntValue(0);
    }
}
