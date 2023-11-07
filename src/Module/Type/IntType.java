package Type;

public class IntType implements TypeInterface{
public boolean equals(Object another) {
        return another instanceof IntType;
    }

    public String toString() {
        return "int";
    }
}
