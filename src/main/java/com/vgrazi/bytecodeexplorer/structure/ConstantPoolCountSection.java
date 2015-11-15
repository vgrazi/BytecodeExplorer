package com.vgrazi.bytecodeexplorer.structure;

import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ConstantPoolCountSection implements ClassFileSection {
    /**
     * Actually the pool size + 1, as presented in the classfile
     */
    private int poolSizeBytes;
    public ConstantPoolCountSection(byte[] bytes) {
        poolSizeBytes = Utils.getTwoBytes(bytes, getStartByteIndex());
    }

    public int getPoolSizeBytes() {
        return poolSizeBytes;
    }

    /**
     * How many elements in this section
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
}
