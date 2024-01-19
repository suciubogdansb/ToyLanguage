package com.suciubogdan.demo2.Module.Type;

import com.suciubogdan.demo2.Module.Value.StringValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

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
