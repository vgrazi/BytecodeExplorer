package com.vgrazi.bytecodeexplorer.structure.constantTypes;

import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ConstantUTF8 extends ConstantType {

    private int startByteIndex;
    private int stringLength;
    private String stringValue;
    /**
     * "tag item" is how the documentation refers to the type byte
     *
     * @return
     */
    @Override
    public byte getTag() {
        return 1;
    }

    @Override
    public void setData(byte[] bytes, int index) {
        this.startByteIndex = index;
        stringLength = Utils.getTwoBytes(bytes, index + 1);
        stringValue = new String(bytes, index + 3, stringLength);
    }

    /**
     * How many elements in this section, for example, constant pool has many elements.
     *
     * @return Number of elements in this section
     */
    @Override
    public int elementCount() {
        return 0;
    }

    /**
     * length in bytes of this section
     *
     * @return length in bytes of this section
     */
    @Override
    public int length() {
        return stringValue.length() + 3;
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

    @Override
    public String toString() {
        return getFormattedConstantIndex() + Utils.formatAsFourByteHexString(startByteIndex) + " Utf8\t\t\t" + stringValue;
    }
}

