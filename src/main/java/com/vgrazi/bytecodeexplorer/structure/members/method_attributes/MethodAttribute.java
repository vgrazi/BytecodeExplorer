package com.vgrazi.bytecodeexplorer.structure.members.method_attributes;

//attribute_info {
//    u2 attribute_name_index;
//    u4 attribute_length;
//    u1 info[attribute_length];
//}

import com.vgrazi.bytecodeexplorer.structure.ClassFileSection;
import com.vgrazi.bytecodeexplorer.ui.swing.PointSelector;
import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 1/3/16
 */
public abstract class MethodAttribute implements ClassFileSection, Cloneable{
    private int startByteIndex;
    private int attributeNameIndex;
    private String attributeName;
    private int attributeLength;
    private byte[] bytesArray;

    public MethodAttribute() {
    }


    public String getAttributeName() {
        if(attributeName == null) {
            attributeName = Utils.getDirectString(attributeNameIndex);
        }
        return attributeName;
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

    /**
     * Since all subclasses need this, we define this method here in the base class
     * @param sb
     */
    public void getFormattedNameAndLength(StringBuilder sb) {
        String style = PointSelector.getStyleForByte(startByteIndex, 2);
        sb.append("<tr><td>" + Utils.formatAsFourByteHexString(getStartByteIndex()) +     "</td>" +
            "<td><span style='" + style +"'>" +
            "Name index:" +        "</span></td><td>" +
            "<td><span style='" + style +"'>" +
            "#" + getAttributeNameIndex() + ": \"" + getAttributeName()).append("\"</span></td></tr>");

        style = PointSelector.getStyleForByte(startByteIndex + 2, 4);
        sb.append("<tr><td>" + Utils.formatAsFourByteHexString(getStartByteIndex() + 2) + "</td><td colspan=2><span style='" + style +"'>" +
            "Attributes length:" + "</span></td><td>" +
            "<span style='" + style +"'>" +
            Utils.getFourBytes(getBytes(), getStartByteIndex() + 2)).append("</span></td></tr>");
    }

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
