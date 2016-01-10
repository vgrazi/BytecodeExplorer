package com.vgrazi.bytecodeexplorer.structure.constantTypes;

import com.vgrazi.bytecodeexplorer.ui.swing.PointSelector;
import com.vgrazi.bytecodeexplorer.utils.Utils;

import java.util.List;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ConstantMethodRef extends ConstantType {
    private byte[] bytes;
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
        return 10;
    }

    @Override
    public void setData(byte[] bytes, int index) {
        this.bytes = bytes;
        this.startByteIndex = index;
        this.classIndex = Utils.getTwoBytes(bytes, index + 1);
        this.nameAndTypeIndex = Utils.getTwoBytes(bytes, index + 3);
    }

    public String toString() {
        List<ConstantType> constants = getConstants();
        String string = getFormattedAddressAndConstantIndex() +
            "<span style='" + PointSelector.getStyleForByte(startByteIndex, 1) +"'>" + Utils.formatAsOneByteHexString(getTag()) + ": Method ref</span><br/>" +
            " <span style='color:blue; " + PointSelector.getStyleForByte(startByteIndex + 1, 1) + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByteIndex + 1)) + "</span>" +
            " <span style='color:blue; " + PointSelector.getStyleForByte(startByteIndex + 2, 1) + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByteIndex + 2)) + "</span>=(#" + classIndex + ") " + ((ConstantClass) constants.get(classIndex - 1)).getConstantValue() +
            "</span><br/>" +
            " <span style='color:red; " + PointSelector.getStyleForByte(startByteIndex + 3, 1) + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByteIndex + 3)) + "</span>" +
            " <span style='color:red; " + PointSelector.getStyleForByte(startByteIndex + 4, 1) + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByteIndex + 4)) + "</span>=(#" + nameAndTypeIndex + ") " + ((ConstantNameAndTypeInfo)constants.get(nameAndTypeIndex - 1)).getValue() + "</span>";
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
