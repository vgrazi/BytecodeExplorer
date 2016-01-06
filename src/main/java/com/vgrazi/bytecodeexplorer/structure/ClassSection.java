package com.vgrazi.bytecodeexplorer.structure;

import com.vgrazi.bytecodeexplorer.structure.constantTypes.ConstantType;
import com.vgrazi.bytecodeexplorer.utils.Utils;

import java.util.List;

/**
 * Created by vgrazi on 11/22/15.
 */
public class ClassSection implements ClassFileSection {
    private final int classIndex;
    private final String name;
    private int startByte;

    public ClassSection(String name, byte[] bytes, int startByte) {
        this.name = name;

        this.startByte = startByte;
        classIndex = Utils.getTwoBytes(bytes, getStartByteIndex());
    }

    @Override
    public int length() {
        return 2;
    }

    @Override
    public int getStartByteIndex() {
        return startByte;
    }

    public String getName() {
        return name;
    }


    public String toString() {
        String rval = Utils.formatAsFourByteHexString(getStartByteIndex()) + " " +
            name + " " +
            "<span style='color:blue'>#" + classIndex + "</span>";
        List<ConstantType> constants = ConstantPoolSection.getConstants();
        if (constants != null) {
            rval += "<br/>" +
                "<span style='color:blue'>" + constants.get(classIndex - 1) + "</span><br/>";

        }

        return rval;
    }

}
