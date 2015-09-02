package com.vgrazi.bytecodeexplorer.structure.constantTypes;

import com.vgrazi.bytecodeexplorer.util.Utils;

import java.util.List;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ConstantNameAndTypeInfo implements ConstantType {

    private int startByteIndex;
    private int nameIndex;
    private int descriptorIndex;

    public ConstantNameAndTypeInfo() {

    }

    /**
     * "tag item" is how the documentation refers to the type byte
     *
     * @return
     */
    @Override
    public byte getTag() {
        return 12;
    }

    public void setData(byte[] bytes, int index) {
        this.startByteIndex = index;
        this.nameIndex = Utils.getTwoBytes(bytes, index + 1);
        this.descriptorIndex = Utils.getTwoBytes(bytes, index + 3);
    }

    @Override
    public String toString(List<ConstantType> constants) {
        return "NameAndType #" + nameIndex + ":#" + descriptorIndex + " // " + constants.get(nameIndex -1).toString(constants) +
            " : " + constants.get(descriptorIndex -1).toString(constants);
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

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
