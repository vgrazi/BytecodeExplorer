package com.vgrazi.bytecodeexplorer.structure;

/**
 * Created by vgrazi on 8/13/15.
 */
public interface ClassFileSection {
    /**
     * How many elements in this section, for example, constant pool has many elements.
     *
     * @return Number of elements in this section
     */
    int elementCount();

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
}
