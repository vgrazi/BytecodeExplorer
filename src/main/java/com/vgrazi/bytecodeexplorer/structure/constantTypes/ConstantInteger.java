package com.vgrazi.bytecodeexplorer.structure.constantTypes;

import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ConstantInteger extends ConstantType {
    private int startByteIndex;
    private int intValue;

    /**
     * "tag item" is how the documentation refers to the type byte
     *
     * @return
     */
    @Override
    public byte getTag() {
        return 3;
    }

    public void setData(byte[] bytes, int index) {
        this.startByteIndex = index;
        this.intValue = (int) Utils.getFourBytes(bytes, index+1);
    }

    public String toString() {
        String string = getFormattedAddressAndConstantIndex() + "ConstantInteger\t\t\t" + intValue + "<br/>";
        return string;
    }

    /**
     * length in bytes of this section
     *
     * @return length in bytes of this section
     */
    @Override
    public int length() {
        return 5;
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
