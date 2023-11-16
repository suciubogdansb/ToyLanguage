package Module.Type;

import Module.Value.StringValue;
import Module.Value.ValueInterface;

public class StringType implements TypeInterface{
    public boolean equals(Object another) {
        return another instanceof StringType;
    }

    public String toString() {
        return "string";
    }

    @Override
    public ValueInterface defaultValue() {
        return new StringValue("");
    }
}
