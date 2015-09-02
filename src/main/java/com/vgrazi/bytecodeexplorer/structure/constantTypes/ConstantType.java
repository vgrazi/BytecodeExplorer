package com.vgrazi.bytecodeexplorer.structure.constantTypes;

import com.vgrazi.bytecodeexplorer.structure.ClassFileSection;

import java.util.List;

/**
 * Created by vgrazi on 8/13/15.
 */
public interface ConstantType extends ClassFileSection, Cloneable {
    /**
     * "tag item" is how the documentation refers to the type byte
     *
     * @return
     */
    byte getTag();

    Object clone();

    void setData(byte[] bytes, int index);

    String toString(List<ConstantType> constants);
}