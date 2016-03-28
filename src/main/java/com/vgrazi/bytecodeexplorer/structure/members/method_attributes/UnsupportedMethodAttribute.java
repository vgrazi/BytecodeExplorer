package com.vgrazi.bytecodeexplorer.structure.members.method_attributes;

/**
 * Created by vgrazi on 1/3/16.
 */
public class UnsupportedMethodAttribute extends MethodAttribute {
    private String attribute;

    public UnsupportedMethodAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getIdentifier() {
        return null;
    }

    @Override
    public String toString() {
        return attribute + " not supported";
    }
}
