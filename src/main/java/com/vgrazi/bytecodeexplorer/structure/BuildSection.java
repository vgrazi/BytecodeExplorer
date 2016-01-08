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
    public int length() {
        return 2;
    }

    @Override
    public int getStartByteIndex() {
        return startByte;
    }

    public String toString() {
        return Utils.getAddress(startByte) + " " + kind + " build number " + Utils.formatAsFourByteHexString(build);
    }
}
