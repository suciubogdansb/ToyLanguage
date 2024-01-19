package com.suciubogdan.demo2.Module.Type;

import com.suciubogdan.demo2.Module.Value.IntValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

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
