package Module.Type;

import Module.Value.ValueInterface;

public interface TypeInterface {
    public boolean equals(Object another);
    public String toString();

    public ValueInterface defaultValue();
}
