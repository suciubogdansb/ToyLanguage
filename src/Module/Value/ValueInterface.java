package Module.Value;

import Module.Type.TypeInterface;

public interface ValueInterface {
    TypeInterface getType();

    ValueInterface deepCopy();

    boolean equals(Object another);
}
