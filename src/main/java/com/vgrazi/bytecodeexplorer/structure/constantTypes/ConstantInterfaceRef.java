package com.vgrazi.bytecodeexplorer.structure.constantTypes;

import com.vgrazi.bytecodeexplorer.utils.Utils;

import java.util.List;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ConstantInterfaceRef extends ConstantType {
    private int startByteIndex;
    private int classIndex;
    private int nameAndTypeIndex;

    /**
     * "tag item" is how the documentation refers to the type byte
     *
     * @return
     */
    @Override
    public byte getTag() {
        return 11;
    }

    @Override
    public void setData(byte[] bytes, int index) {
        startByteIndex = index;
        classIndex = Utils.getTwoBytes(bytes, index + 1);
        nameAndTypeIndex = Utils.getTwoBytes(bytes, index + 3);
    }

    public String toString() {
        List<ConstantType> constants = getConstants();
        String string = getFormattedConstantIndex() + Utils.formatAsFourByteHexString(startByteIndex);
        if (constants != null) {
            string +=
                "<span style='color:blue'>" + constants.get(classIndex - 1) + "</span><br/>" +
                "<span style='color:red'>" + constants.get(nameAndTypeIndex - 1) + "</span><br/>";
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
        return 0;
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
