package com.vgrazi.bytecodeexplorer.structure.constantTypes;

import com.vgrazi.bytecodeexplorer.ui.swing.PointSelector;
import com.vgrazi.bytecodeexplorer.utils.IEEE754Converter;
import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 8/13/15
 */
public class ConstantFloat extends ConstantType {
    private int IEE754;
    private byte[] bytes;
    private int startByteIndex;
    private String explanation;
    private double value;
    private byte signBit;
    private long exponent;
    private double mantissa;
    private long mantissaBytes;

    /**
     * "tag item" is how the documentation refers to the type byte
     *
     * @return 4
     */
    @Override
    public byte getTag() {
        return 4;
    }

    @Override
    public void setData(byte[] bytes, int index) {
        this.bytes = bytes;
        this.startByteIndex = index;
        IEE754 = Utils.getFourBytes(bytes, index + 1);
        value = IEEE754Converter.convertToDecimal(IEE754);
        signBit = IEEE754Converter.extractSignBit(IEE754);
        exponent = IEEE754Converter.extractExponent(IEE754);
        mantissa = IEEE754Converter.extractMantissa(IEE754, exponent);
        mantissaBytes = IEEE754Converter.extractMantissaBytes(IEE754);
    }

    @Override
    public String toString() {
        explanation =
            "<span  style='" + PointSelector.getStyleForByte(startByteIndex, 1)    + "'>" + Utils.formatAsOneByteHexString(getTag())  + ": Constant Float<br/></span>" +
            "<span style='" + PointSelector.getStyleForByte(startByteIndex + 1, 1) + "'> "+ Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByteIndex + 1)) +  "</span>" +
            "<span style='" + PointSelector.getStyleForByte(startByteIndex + 2, 1) + "'> "+ Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByteIndex + 2)) +  "</span>" +
            "<span style='" + PointSelector.getStyleForByte(startByteIndex + 3, 1) + "'> "+ Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByteIndex + 3)) +  "</span>" +
            "<span style='" + PointSelector.getStyleForByte(startByteIndex + 4, 1) + "'> "+ Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByteIndex + 4)) +  "</span>=<br/>" +

            "<span style='" + PointSelector.getStyleForByte(startByteIndex + 1, 1) + "'>&nbsp;"+ Utils.formatAsBinary(Utils.getOneByte(bytes, startByteIndex + 1)).substring(10, 19) +  "</span>" +
            "<span style='" + PointSelector.getStyleForByte(startByteIndex + 2, 1) + "'> "+ Utils.formatAsBinary(Utils.getOneByte(bytes, startByteIndex + 2)).substring(10, 19) +  "</span>" +
            "<span style='" + PointSelector.getStyleForByte(startByteIndex + 3, 1) + "'> "+ Utils.formatAsBinary(Utils.getOneByte(bytes, startByteIndex + 3)).substring(10, 19) +  "</span>" +
            "<span style='" + PointSelector.getStyleForByte(startByteIndex + 4, 1) + "'> "+ Utils.formatAsBinary(Utils.getOneByte(bytes, startByteIndex + 4)).substring(10, 19) +  "</span>" +
            IEEE754Converter.getExplanation(IEE754, value, signBit, exponent, mantissa, mantissaBytes);
        String string = getFormattedAddressAndConstantIndex() + explanation;

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
     * @return the start byte index for this section
     */
    @Override
    public int getStartByteIndex() {
        return startByteIndex;
    }
}
