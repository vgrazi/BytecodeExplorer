package com.vgrazi.bytecodeexplorer.structure.constantTypes;

import com.vgrazi.bytecodeexplorer.utils.Utils;

import java.util.List;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ConstantClass extends ConstantType {

    private int startByteIndex;
    private int nameIndex;

    /**
     * "tag item" is how the documentation refers to the type byte
     *
     * @return
     */
    @Override
    public byte getTag() {
        return 7;
    }

    public void setData(byte[] bytes, int index) {
        this.startByteIndex = index;
        this.nameIndex = Utils.getTwoBytes(bytes, index + 1);
    }

    @Override
    public String toString() {
        List<ConstantType> constants = getConstants();
        String string = getFormattedAddressAndConstantIndex() + " Class #" + nameIndex;

        if (constants != null) {
            String value = getConstantValue();
            string += " " + value;
        }
        return string;
    }

    public String getConstantValue() {
        List<ConstantType> constants = getConstants();
        ConstantUTF8 utf = ((ConstantUTF8) constants.get(nameIndex - 1));
        return utf.getStringValue();
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
