package Module.Value;

import Module.Type.ReferenceType;
import Module.Type.TypeInterface;

public class ReferenceValue implements ValueInterface{
    int address;
    TypeInterface locationType;

    public ReferenceValue(int address, TypeInterface locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public TypeInterface getLocationType() {
        return locationType;
    }

    @Override
    public TypeInterface getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public ValueInterface deepCopy() {
        return new ReferenceValue(address, locationType);
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof ReferenceValue) {
            return this.address == ((ReferenceValue) another).address && this.locationType.equals(((ReferenceValue) another).locationType);
        }
        return false;
    }

    @Override
    public String toString() {
        return "( " + address + ", " + locationType.toString() + " )";
    }
}
