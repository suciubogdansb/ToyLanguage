package com.suciubogdan.demo2.Module.Type;

import com.suciubogdan.demo2.Module.Value.ReferenceValue;
import com.suciubogdan.demo2.Module.Value.ValueInterface;

public class ReferenceType implements TypeInterface{

    TypeInterface inner;

    public ReferenceType() {
        inner = null;
    }
    public ReferenceType(TypeInterface inner) {
        this.inner = inner;
    }

    public TypeInterface getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof ReferenceType) {
            return inner.equals(((ReferenceType) another).getInner());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public ValueInterface defaultValue() {
        return new ReferenceValue(0, inner);
    }

}

