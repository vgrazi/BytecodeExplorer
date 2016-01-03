package com.vgrazi.bytecodeexplorer.structure.members.method_attributes;

//attribute_info {
//    u2 attribute_name_index;
//    u4 attribute_length;
//    u1 info[attribute_length];
//}

import com.vgrazi.bytecodeexplorer.structure.ClassFileSection;
import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 1/3/16
 */
public abstract class MethodAttribute implements ClassFileSection, Cloneable{
    private int startByteIndex;
    private int attributeNameIndex;
    private int attributeLength;
    private byte[] bytesArray;

    public MethodAttribute() {
    }


    public String getAttributeName() {
        return Utils.getDirectString(attributeNameIndex);
    }

    public int getAttributeNameIndex() {
        return attributeNameIndex;
    }

    public int getAttributeLength() {
        return attributeLength;
    }


    //    u2 attribute_name_index;
//    u4 attribute_length;

    public int length() {
        return attributeLength + 6;
    }

    @Override
    public int getStartByteIndex() {
        return startByteIndex;
    }

    public void setData(byte[] bytesArray, int startByte) {
        this.bytesArray = bytesArray;
        this.startByteIndex = startByte;
        attributeNameIndex = Utils.getTwoBytes(bytesArray, startByte);
        attributeLength = Utils.getFourBytes(bytesArray, startByte + 2);
    }

    public byte[] getBytes() {
        return bytesArray;
    }
    /**
     * returns the identifier (eg Code, Exceptions, etc) of this method attribute
     * @return the identifier (eg Code, Exceptions, etc) of this method attribute
     */
    public abstract String getIdentifier();

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
