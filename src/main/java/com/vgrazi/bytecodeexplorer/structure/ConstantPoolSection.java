package com.vgrazi.bytecodeexplorer.structure;

import com.vgrazi.bytecodeexplorer.structure.constantTypes.ConstantType;
import com.vgrazi.bytecodeexplorer.utils.ConstantTypeFactory;
import com.vgrazi.bytecodeexplorer.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ConstantPoolSection implements ClassFileSection {

    private int startByteIndex;
    private int length;

    private int elementCount;
    private final static List<ConstantType> constants = new ArrayList<>();

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

    public int getSectionIndex(int byteIndex) {
        for(int i = 0; i < constants.size(); i++) {
            ConstantType constant = constants.get(i);
            if(constant.contains(byteIndex)) {
                return i;
            }
        }
        return 0;
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

    public ClassFileSection getSection(int sectionIndex) {
        ConstantType type = null;
        if (sectionIndex < constants.size()) {
            type = constants.get(sectionIndex);
        }
        return type;
    }

    public static List<ConstantType> getConstants() {
        return constants;
    }
}
