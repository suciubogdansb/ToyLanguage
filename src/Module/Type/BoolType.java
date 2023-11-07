package Type;

public class BoolType implements TypeInterface{
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    public String toString() {
        return "bool";
    }
}
