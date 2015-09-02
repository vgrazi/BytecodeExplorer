package com.vgrazi.bytecodeexplorer.structure;

import com.vgrazi.bytecodeexplorer.util.Utils;

/**
 * Created by vgrazi on 8/13/15.
 */
public class MajorBuildSection implements ClassFileSection {
    private int build;
    public MajorBuildSection(byte[] bytes) {
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
        return 6;
    }
}
