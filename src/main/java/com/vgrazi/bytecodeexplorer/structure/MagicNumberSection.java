package com.vgrazi.bytecodeexplorer.structure;

import com.vgrazi.bytecodeexplorer.ui.swing.PointSelector;
import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 8/13/15.
 */
public class MagicNumberSection implements ClassFileSection {
    private int magicNumber;
    private byte[] bytes;

    public MagicNumberSection(byte[] bytes) {
        this.bytes = bytes;
        magicNumber = Utils.getFourBytes(bytes, getStartByteIndex());
    }

    public long getMagicNumber() {
        return magicNumber;
    }

    @Override
    public int length() {
        return 4;
    }

    @Override
    public int getStartByteIndex() {
        return 0;
    }

    public String toString() {
        int startByte = getStartByteIndex();
        return Utils.formatAsFourByteHexString(startByte) + " Magic number: " +
            "<span style='" + PointSelector.getStyleForByte(startByte, 1)     + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByte)) + "</span>" +
            "<span style='" + PointSelector.getStyleForByte(startByte + 1, 1) + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByte + 1)) + "</span>" +
            "<span style='" + PointSelector.getStyleForByte(startByte + 2, 1) + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByte + 2)) + "</span>" +
            "<span style='" + PointSelector.getStyleForByte(startByte + 3, 1) + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByte + 3)) + "</span> = " + magicNumber;
    }
}
