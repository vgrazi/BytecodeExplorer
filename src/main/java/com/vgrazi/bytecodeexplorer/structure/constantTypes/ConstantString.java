package com.vgrazi.bytecodeexplorer.structure.constantTypes;

import com.vgrazi.bytecodeexplorer.utils.Utils;

import java.util.List;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ConstantString extends ConstantType {
    private int startByteIndex;
    private int stringIndex;

    /**
     * "tag item" is how the documentation refers to the type byte
     *
     * @return
     */
    @Override
    public byte getTag() {
        return 8;
    }

    public void setData(byte[] bytes, int index) {
        this.startByteIndex = index;
        this.stringIndex = Utils.getTwoBytes(bytes, index+1);
    }

    public String toString() {
        List<ConstantType> constants = getConstants();
        String string = getFormattedAddressAndConstantIndex() + "String\t\t\t#" + stringIndex + "<br/>";
        if (constants != null) {
            string += constants.get(stringIndex - 1).toString();
        }
        return string;
    }

    /**
     * length in bytes of this section
     *
     * @return length in bytes of this section
     */
    @Override
    public int length() {
        return 3;
    }

    /**
     * Index to the first byte of this section relative to the constant pool
     *
     * @return
     */
    @Override
    public int getStartByteIndex() {
        return startByteIndex;
    }
}
