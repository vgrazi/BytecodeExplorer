package com.vgrazi.bytecodeexplorer.structure.constantTypes;

import com.vgrazi.bytecodeexplorer.utils.IEEE754Converter;
import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ConstantFloat extends ConstantType {
    private int startByteIndex;
    private int classIndex;
    private int nameAndTypeIndex;
    private byte[] bytes;
    private String explanation;

    /**
     * "tag item" is how the documentation refers to the type byte
     *
     * @return
     */
    @Override
    public byte getTag() {
        return 4;
    }

    @Override
    public void setData(byte[] bytes, int index) {
        this.startByteIndex = index;
        this.classIndex = Utils.getTwoBytes(bytes, index + 1);
        this.nameAndTypeIndex = Utils.getTwoBytes(bytes, index + 3);

        this.bytes = bytes;
        int IEE754 = Utils.getFourBytes(bytes, index + 1);
        double value = IEEE754Converter.convertToDecimal(IEE754);
        byte signBit = IEEE754Converter.extractSignBit(IEE754);
        long exponent = IEEE754Converter.extractExponent(IEE754);
        long mantissa = IEEE754Converter.extractMantissa(IEE754, exponent);
        long mantissaBytes = IEEE754Converter.extractMantissaBytes(IEE754);
        explanation =
            getFormattedAddressAndConstantIndex() + " ConstantFloat<br/>" +
            IEEE754Converter.getExplanation(IEE754, value, signBit, exponent, mantissa, mantissaBytes);
        // todo: continue here
    }

    @Override
    public String toString() {
        return explanation;
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
