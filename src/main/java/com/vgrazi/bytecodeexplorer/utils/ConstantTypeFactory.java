package com.vgrazi.bytecodeexplorer.utils;

import com.vgrazi.bytecodeexplorer.structure.constantTypes.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vgrazi on 8/14/15.
 */
public class ConstantTypeFactory {
    static final protected Map<Byte, ConstantType> constantTypeMap = new HashMap<>();

    static {
        pool(new ConstantUTF8());
        pool(new ConstantFloat());
        pool(new ConstantClass());
        pool(new ConstantString());
        pool(new ConstantFieldRef());
        pool(new ConstantMethodRef());
        pool(new ConstantInterfaceRef());
        pool(new ConstantNameAndTypeInfo());
    }

    public static ConstantType createConstantType(byte tag) {
        ConstantType constantType = constantTypeMap.get(tag);
        ConstantType rval = (ConstantType) constantType.clone();
        return rval;
    }

    private static void pool(ConstantType constantType) {
        constantTypeMap.put(constantType.getTag(), constantType);
    }
}