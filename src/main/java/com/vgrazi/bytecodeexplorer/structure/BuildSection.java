package com.vgrazi.bytecodeexplorer.structure;

import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 8/13/15.
 */
public class BuildSection implements ClassFileSection {
    private final int build;
    private String kind;
    private int startByte;

    public BuildSection(String kind, byte[] bytes, int startByte) {
        this.kind = kind;
        this.startByte = startByte;
        build = Utils.getTwoBytes(bytes, getStartByteIndex());
    }

    @Override
    public int elementCount() {
        return 1;
    }

    @Override
    public int length() {
        return 2;
    }

    @Override
    public int getStartByteIndex() {
        return startByte;
    }

    public boolean contains(int startByteIndex) {
        return startByteIndex >= getStartByteIndex() && startByteIndex < getStartByteIndex() + length();
    }

    public String toString() {
        return Utils.formatAsFourByteHexString(startByte) + " " + kind + " build number " + Utils.formatAsFourByteHexString(build);
    }
}
