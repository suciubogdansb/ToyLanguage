package com.suciubogdan.demo2.Module.Value;

import com.suciubogdan.demo2.Module.Type.BoolType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;

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
