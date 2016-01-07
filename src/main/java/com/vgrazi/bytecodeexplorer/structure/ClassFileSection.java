package com.vgrazi.bytecodeexplorer.structure;

import com.vgrazi.bytecodeexplorer.ui.swing.PointSelector;
import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 8/13/15.
 */
public interface ClassFileSection {

    /**
     * length in bytes of this section
     *
     * @return length in bytes of this section
     */
    int length();

    /**
     * Index to the first byte of this section relative to the constant pool
     */
    int getStartByteIndex();

    default int getEndByteIndex() {
        return getStartByteIndex() + length();
    }

    default boolean contains(int startByteIndex) {
        return startByteIndex >= getStartByteIndex() && startByteIndex < getStartByteIndex() + length();
    }

    default String getAddress(int index) {
        String style = "";
        if (index == PointSelector.getMouseByteIndex()) {
            style = "style='font-weight:bold'";
        }
        return "<span " + style + ">" + Utils.formatAsFourByteHexString(index) + "</span>";
    }
}
