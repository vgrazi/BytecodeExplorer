package com.vgrazi.bytecodeexplorer.structure.constantTypes;

import com.vgrazi.bytecodeexplorer.utils.IEEE754Converter;
import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 8/13/15
 */
public class ConstantFloat extends ConstantType {
    private int startByteIndex;
    private String explanation;

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
        this.startByteIndex = index;
        int IEE754 = Utils.getFourBytes(bytes, index + 1);
        double value = IEEE754Converter.convertToDecimal(IEE754);
        byte signBit = IEEE754Converter.extractSignBit(IEE754);
        long exponent = IEEE754Converter.extractExponent(IEE754);
        double mantissa = IEEE754Converter.extractMantissa(IEE754, exponent);
        long mantissaBytes = IEEE754Converter.extractMantissaBytes(IEE754);
        explanation = getFormattedAddressAndConstantIndex() + " ConstantFloat<br/>" +
            IEEE754Converter.getExplanation(IEE754, value, signBit, exponent, mantissa, mantissaBytes);
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
     * @return the start byte index for this section
     */
    @Override
    public int getStartByteIndex() {
        return startByteIndex;
    }
}
