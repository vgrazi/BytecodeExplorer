package com.vgrazi.bytecodeexplorer.structure;

import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 8/13/15.
 */
public class MagicNumberSection implements ClassFileSection {
    private long magicNumber;

    public MagicNumberSection(byte[] bytes) {
        magicNumber = Utils.getFourBytes(bytes, getStartByteIndex());
    }

    public long getMagicNumber() {
        return magicNumber;
    }

    @Override
    public int elementCount() {
        return 1;
    }

    @Override
    public int length() {
        return 4;
    }

    @Override
    public int getStartByteIndex() {
        return 0;
    }
}
