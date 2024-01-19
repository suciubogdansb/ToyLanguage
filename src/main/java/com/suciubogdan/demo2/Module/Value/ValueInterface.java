package com.suciubogdan.demo2.Module.Value;

import com.suciubogdan.demo2.Module.Type.TypeInterface;

public interface ValueInterface {
    TypeInterface getType();

    ValueInterface deepCopy();

    boolean equals(Object another);
}
