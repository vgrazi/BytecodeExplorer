package com.vgrazi.bytecodeexplorer.structure;

import com.vgrazi.bytecodeexplorer.structure.constantTypes.ConstantType;
import com.vgrazi.bytecodeexplorer.util.ConstantTypeFactory;
import com.vgrazi.bytecodeexplorer.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ConstantPoolSection implements ClassFileSection {

    private int startByteIndex;
    private int length;

    private int elementCount;
    private final List<ConstantType> constants = new ArrayList<>();

    public ConstantPoolSection(int elementCount, byte[] bytes, int index) {

        setElementCount(elementCount);
        this.startByteIndex = index;
        ConstantType constantType = null;
        for(int i = 0; i < elementCount -1; i++) {
            byte tag = bytes[index];
            constantType = ConstantTypeFactory.createConstantType(tag);
            constantType.setData(bytes, index);
            constants.add(constantType);
            index += constantType.length();
        }
        if (constantType != null) {
            length = constantType.getStartByteIndex() + constantType.length() - startByteIndex;
        }

        Utils.printConstants(constants);
    }

    /**
     * How many elements in this section
     *
     * @return Number of elements in this section
     */
    @Override
    public int elementCount() {
        return elementCount;
    }

    public void setElementCount(int elementCount) {
        this.elementCount = elementCount;
    }

    /**
     * length in bytes of this section
     *
     * @return length in bytes of this section
     */
    @Override
    public int length() {
        return length;
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

    /**
     * index to the first byte following this section, relative to the constant pool
     *
     * @return
     */
    public int getNextByteIndex() {
        return 0;
    }
}
