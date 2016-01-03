package com.vgrazi.bytecodeexplorer.utils;

import com.vgrazi.bytecodeexplorer.structure.members.method_attributes.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vgrazi on 1/3/16
 */
public class MethodAttributeFactory {

    private static final Map<String, MethodAttribute> attributeMap = new HashMap<>();
    static {

        pool(new ConstantValueMethodAttribute());
        pool(new CodeMethodAttribute());
        pool(new StackMapTableMethodAttribute());
        pool(new ExceptionsMethodAttribute());
        pool(new InnerClassesMethodAttribute());
        pool(new EnclosingMethodMethodAttribute());
        pool(new SyntheticMethodAttribute());
        pool(new SignatureMethodAttribute());
        pool(new SourceFileMethodAttribute());
        pool(new SourceDebugExtensionMethodAttribute());
        pool(new LineNumberTableMethodAttribute());
        pool(new LocalVariableTableMethodAttribute());
        pool(new LocalVariableTypeTableMethodAttribute());
        pool(new DeprecatedMethodAttribute());
        pool(new RuntimeVisibleAnnotationsMethodAttribute());
        pool(new RuntimeInvisibleAnnotationsMethodAttribute());
        pool(new RuntimeVisibleParameterAnnotationsMethodAttribute());
        pool(new RuntimeInvisibleParameterAnnotationsMethodAttribute());
        pool(new AnnotationDefaultMethodAttribute());
        pool(new BootstrapMethodMethodAttribute());
    }

    public static MethodAttribute createMethodAttribute(byte[] bytesArray, int startByte) {
        int attributeNameIndex = Utils.getTwoBytes(bytesArray, startByte);
        String attributeName = Utils.getDirectString(attributeNameIndex);


        MethodAttribute methodAttribute = attributeMap.get(attributeName);
        MethodAttribute rval;
        if (methodAttribute != null) {
            rval = (MethodAttribute) methodAttribute.clone();
        }
        else {
            rval = new UnsupportedMethodAttribute();
        }
        rval.setData(bytesArray, startByte);
        return rval;
    }

    private static void pool(MethodAttribute methodAttribute) {
        attributeMap.put(methodAttribute.getIdentifier(), methodAttribute);
    }

}
