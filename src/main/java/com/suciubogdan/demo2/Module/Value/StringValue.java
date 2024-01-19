package com.suciubogdan.demo2.Module.Value;

import com.suciubogdan.demo2.Module.Type.StringType;
import com.suciubogdan.demo2.Module.Type.TypeInterface;

public class StringValue implements ValueInterface{
    private String value;

    public StringValue(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public String toString(){
        return this.value;
    }

    @Override
    public TypeInterface getType() {
        return new StringType();
    }

    @Override
    public ValueInterface deepCopy() {
        return new StringValue(this.value);
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof StringValue) {
            return this.value.equals(((StringValue) another).getValue());
        }
        return false;
    }
}
