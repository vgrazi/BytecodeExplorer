package com.vgrazi.bytecodeexplorer.structure.constantTypes;

import com.vgrazi.bytecodeexplorer.ui.swing.PointSelector;
import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ConstantUTF8 extends ConstantType {

    private byte[] bytes;
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
        this.bytes = bytes;
        this.startByteIndex = index;
        stringLength = Utils.getTwoBytes(bytes, index + 1);
        stringValue = new String(bytes, index + 3, stringLength);
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

    public String getStringValue() {
        return stringValue;
    }

    @Override
    public String toString() {
        String rval = Utils.formatAsFourByteHexString(getStartByteIndex()) + " " + String.format("(#%d) ", getIndex());
        String style = PointSelector.getStyleForByte(startByteIndex, 1);
        rval += "<span style='" + style + "'>" + Utils.formatAsOneByteHexString(getTag()) + "=Utf8 </span>";
        rval += "Length: <span style='" + PointSelector.getStyleForByte(startByteIndex + 1, 1) + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByteIndex + 1)) + "</span>";
        rval += "<span style='" + PointSelector.getStyleForByte(startByteIndex + 2, 1) + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByteIndex + 2)) + "</span>";
        rval += "= " + stringLength + " ";
        int mouseByteIndex = PointSelector.getMouseByteIndex();
        if(mouseByteIndex >= startByteIndex + 3 && mouseByteIndex <= startByteIndex + 2 + stringLength)
        {
            style = PointSelector.getStyleForByte(mouseByteIndex, 1);
            if (mouseByteIndex - startByteIndex - 3 >= 0)
            {
                rval += stringValue.substring(0, mouseByteIndex - startByteIndex - 3).replaceAll("<", "&lt;");
            }

            rval += "<span style='" + style + "'>" +
                stringValue.substring(mouseByteIndex - startByteIndex -3, mouseByteIndex - startByteIndex -2).replaceAll("<", "&lt;") + "</span>";

            if (mouseByteIndex - startByteIndex -2 >=0 && mouseByteIndex - startByteIndex -2 < stringLength)
            {
                rval += stringValue.substring(mouseByteIndex - startByteIndex -2).replaceAll("<", "&lt;");
            }
        }
        else {
            rval += stringValue.replaceAll("<", "&lt;");
        }
        return rval;

    }
}

