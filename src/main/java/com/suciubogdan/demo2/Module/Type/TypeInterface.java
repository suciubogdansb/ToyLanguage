package com.suciubogdan.demo2.Module.Type;

import com.suciubogdan.demo2.Module.Value.ValueInterface;

public interface TypeInterface {
    public boolean equals(Object another);
    public String toString();

    public ValueInterface defaultValue();
}
