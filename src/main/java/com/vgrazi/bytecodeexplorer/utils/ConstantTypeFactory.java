package com.vgrazi.bytecodeexplorer.utils;

import com.vgrazi.bytecodeexplorer.structure.constantTypes.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vgrazi on 8/14/15.
 */
public class ConstantTypeFactory {
    static final protected Map<Byte, ConstantType> constantTypeMap = new HashMap<>();

    private static int constantIndex = 1;
    static {
        pool(new ConstantUTF8());                   //  1


        pool(new ConstantFloat());                  //  4


        pool(new ConstantClass());                  //  7
        pool(new ConstantString());                 //  8
        pool(new ConstantFieldRef());               //  9
        pool(new ConstantMethodRef());              // 10
        pool(new ConstantInterfaceRef());           // 11
        pool(new ConstantNameAndTypeInfo());        // 12
    }

    public static ConstantType createConstantType(byte tag) {
        ConstantType constantType = constantTypeMap.get(tag);
        ConstantType rval = (ConstantType) constantType.clone();
        rval.setIndex(constantIndex++);
        return rval;
    }

    private static void pool(ConstantType constantType) {
        constantTypeMap.put(constantType.getTag(), constantType);
    }
}
