package com.vgrazi.bytecodeexplorer.structure;

import com.vgrazi.bytecodeexplorer.ui.swing.PointSelector;
import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 8/13/15.
 */
public class CountSection implements ClassFileSection {
    private final String name;
    private byte[] bytes;
    private final int startByteIndex;
    /**
     * Actually the pool size + 1, as presented in the classfile
     */
    private int countBytes;

    public CountSection(String name, byte[] bytes, int startByteIndex) {
        this.bytes = bytes;
        this.startByteIndex = startByteIndex;
        countBytes = Utils.getTwoBytes(bytes, startByteIndex);
        this.name = name;
    }

    public int getCount() {
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
        return startByteIndex;
    }

    @Override
    public String toString() {
        return Utils.formatAsFourByteHexString(startByteIndex) + " " + name + " " +
            "<span style='" + PointSelector.getStyleForByte(startByteIndex, 1)     + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByteIndex)) + "</span>" +
            "<span style='" + PointSelector.getStyleForByte(startByteIndex + 1, 1) + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByteIndex + 1)) + "</span> = " + countBytes;
    }

}
