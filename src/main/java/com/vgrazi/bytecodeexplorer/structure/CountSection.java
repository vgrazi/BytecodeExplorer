package com.vgrazi.bytecodeexplorer.structure;

import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 8/13/15.
 */
public class CountSection implements ClassFileSection {
    /**
     * Actually the pool size + 1, as presented in the classfile
     */
    private int countBytes;
    public CountSection(byte[] bytes) {
        countBytes = Utils.getTwoBytes(bytes, getStartByteIndex());
    }

    public int getCountBytes() {
        return countBytes;
    }

    /**
     * length in bytes of this section
     *
     * @return length in bytes of this section
     */
    @Override
    public int length() {
        return 2;
    }

    /**
     * Index to the first byte of this section relative to the constant pool
     *
     * @return
     */
    @Override
    public int getStartByteIndex() {
        return 8;
    }

    @Override
    public String toString() {
        return Utils.formatAsFourByteHexString(getStartByteIndex()) + " Pool count:" + countBytes;
    }
}
