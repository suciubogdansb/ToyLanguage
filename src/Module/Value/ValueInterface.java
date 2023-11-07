package Module.Value;

import Module.Type.TypeInterface;

public interface ValueInterface {
    TypeInterface getType();

    ValueInterface deepCopy();
}
