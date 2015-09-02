package com.vgrazi.bytecodeexplorer.structure;

import com.vgrazi.bytecodeexplorer.util.Utils;

/**
 * Created by vgrazi on 8/13/15.
 */
public class MinorBuildSection implements ClassFileSection {
    private final int build;

    public MinorBuildSection(byte[] bytes) {
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
        return 4;
    }
}
