package com.vgrazi.bytecodeexplorer.structure.constantTypes;

import com.vgrazi.bytecodeexplorer.ui.swing.PointSelector;
import com.vgrazi.bytecodeexplorer.utils.Utils;

import java.util.List;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ConstantString extends ConstantType {
    private byte[] bytes;
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
        this.bytes = bytes;
        this.startByteIndex = index;
        this.stringIndex = Utils.getTwoBytes(bytes, index+1);
    }

    public String toString() {
        List<ConstantType> constants = getConstants();
        String string = getFormattedAddressAndConstantIndex() +
            "<span  style='" + PointSelector.getStyleForByte(startByteIndex, 1) + "'>" + Utils.formatAsOneByteHexString(getTag()) + ": String ref </span>" +
            "<br/><span style='" + PointSelector.getStyleForByte(startByteIndex + 1, 1) + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByteIndex + 1)) + "</span>" +
            " <span style='" + PointSelector.getStyleForByte(startByteIndex + 2, 1) + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByteIndex + 2)) + "</span>" +
            "=(#" + stringIndex + "): ";

        if (constants != null) {
            string += (((ConstantUTF8) constants.get(stringIndex - 1))).getStringValue();
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
