package com.vgrazi.bytecodeexplorer.utils;

import com.vgrazi.bytecodeexplorer.structure.constantTypes.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vgrazi on 8/14/15.
 *
 * Constant Type	Value
 * CONSTANT_Utf8	1
 * CONSTANT_Integer	3
 * CONSTANT_Float	4
 * CONSTANT_Long	5
 * CONSTANT_Double	6
 * CONSTANT_Class	7
 * CONSTANT_String	8
 * CONSTANT_Fieldref	   9
 * CONSTANT_Methodref	  10
 * CONSTANT_InterfaceMethodref	11
 * CONSTANT_NameAndType	  12
 * CONSTANT_MethodHandle  15
 * CONSTANT_MethodType	  16
 * CONSTANT_InvokeDynamic 18
 */
public class ConstantTypeFactory {
    static final protected Map<Byte, ConstantType> constantTypeMap = new HashMap<>();

    private static int constantIndex = 1;
    static {
        pool(new ConstantUTF8());                   //  1


        pool(new ConstantInteger());                //  3
        pool(new ConstantFloat());                  //  4

        pool(new ConstantClass());                  //  7
        pool(new ConstantString());                 //  8
        pool(new ConstantFieldRef());               //  9
        pool(new ConstantMethodRef());              // 10
        pool(new ConstantInterfaceRef());           // 11
        pool(new ConstantNameAndTypeInfo());        // 12
        pool(new ConstantMethodHandle());           // 15
//        pool(new ConstantMethodType());             // 16
//        pool(new ConstantInokeDynamic());           // 18
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
