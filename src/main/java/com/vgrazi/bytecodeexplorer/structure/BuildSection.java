package com.vgrazi.bytecodeexplorer.structure;

import com.vgrazi.bytecodeexplorer.ui.swing.PointSelector;
import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 8/13/15.
 */
public class BuildSection implements ClassFileSection {
    private final int build;
    private String kind;
    private byte[] bytes;
    private int startByte;

    public BuildSection(String kind, byte[] bytes, int startByte) {
        this.kind = kind;
        this.bytes = bytes;
        this.startByte = startByte;
        build = Utils.getTwoBytes(bytes, getStartByteIndex());
    }

    @Override
    public int length() {
        return 2;
    }

    @Override
    public int getStartByteIndex() {
        return startByte;
    }

    public String toString() {
        return Utils.formatAsFourByteHexString(startByte) + " " + kind + " build number " +
            "<span style='" + PointSelector.getStyleForByte(startByte, 1) + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByte)) + "</span>" +
            "<span style='" + PointSelector.getStyleForByte(startByte + 1, 1) + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, startByte + 1)) + "</span> = " + build;
    }
}
