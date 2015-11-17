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
        String string = Utils.formatAsFourByteHexString(startByteIndex) + " Class #" + nameIndex;
        if (constants != null) {
            string += "<br/>" +
                " " + constants.get(nameIndex -1);
        }
        return string;
    }

    /**
     * How many elements in this section, for example, constant pool has many elements.
     *
     * @return Number of elements in this section
     */
    @Override
    public int elementCount() {
        return 1;
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
        return 0;
    }

}
