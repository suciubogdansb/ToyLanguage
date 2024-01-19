package com.suciubogdan.demo2.Module.Type;

import com.suciubogdan.demo2.Module.Value.BoolValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class BoolType implements TypeInterface{
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    public String toString() {
        return "bool";
    }

    @Override
    public ValueInterface defaultValue() {
        return new BoolValue(false);
    }
}
