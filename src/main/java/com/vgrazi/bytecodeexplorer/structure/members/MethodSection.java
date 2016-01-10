package com.vgrazi.bytecodeexplorer.structure.members;

//method_info {
//    u2             access_flags;
//    u2             name_index;
//    u2             descriptor_index;
//    u2             attributes_count;
//    attribute_info attributes[attributes_count];
//}

import com.vgrazi.bytecodeexplorer.structure.ClassFileSection;
import com.vgrazi.bytecodeexplorer.structure.members.method_attributes.MethodAttribute;
import com.vgrazi.bytecodeexplorer.ui.swing.PointSelector;
import com.vgrazi.bytecodeexplorer.utils.MethodAttributeFactory;
import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 1/3/16
 */
public class MethodSection implements ClassFileSection {

    private final int startByteIndex;
    private final int attributesLength;
    private final MethodAttribute[] attributes;
    private final int descriptorIndex;
    private final int attributesCount;
    private final int accessFlags;
    private final int nameIndex;
    private final String name;

    public MethodSection(byte[] bytesArray, int startByte) {
        accessFlags = Utils.getTwoBytes(bytesArray, startByte);
        nameIndex = Utils.getTwoBytes(bytesArray, startByte + 2);
        name = Utils.getDirectString(nameIndex);

        descriptorIndex = Utils.getTwoBytes(bytesArray, startByte + 4);
        attributesCount = Utils.getTwoBytes(bytesArray, startByte + 6);
        attributes = new MethodAttribute[attributesCount];
        int attributesLength = 0;
        int arrayIndex = startByte + 8;
        for (int i = 0; i < attributesCount; i++) {
            MethodAttribute attributeInfo = MethodAttributeFactory.createMethodAttribute(bytesArray, arrayIndex);
            attributes[i] = attributeInfo;
            attributesLength += attributeInfo.length();
            arrayIndex += attributeInfo.length();
        }
        this.attributesLength = attributesLength;
        this.startByteIndex = startByte;
    }

    public int getStartByteIndex() {
        return startByteIndex;
    }

    public int length() {
        return attributesLength + 8;
    }

    public String toString() {
        String name = Utils.getDirectString(nameIndex);
        String descriptor = Utils.getDirectString(descriptorIndex);
        String style = PointSelector.getStyleForByte(startByteIndex, 2);
        String rval = "Method:<br/>" +
            "<table style='table-layout:fixed'>" +// border=1>" +

            "<tr><td class='method-byte-address'>" + Utils.formatAsFourByteHexString(startByteIndex + 0) +
            "</td>";

        rval +=
            "<td class='method-byte-address'" +
                "<span style='" + style + "'>" +
                Utils.formatAsHexString(accessFlags) + "</span></td><td>" +
                "<span style='" + style + "'>" +
                "Access flags:" + Utils.formatAsBinary(accessFlags) + "</span></td></tr>";

        style = PointSelector.getStyleForByte(startByteIndex + 2, 2);

        rval += "<tr><td class='method-byte-address'" +
            ">" + Utils.formatAsFourByteHexString(startByteIndex + 2) +
            "</td><td class='method-byte-address'" +
            "<span style='" + style + "'>" +
            Utils.formatAsHexString(nameIndex) + "</span></td><td>" +
            "<span style='" + style + "'>" +
            "#" + nameIndex + " " + name + "</span></td></tr>";

        style = PointSelector.getStyleForByte(startByteIndex + 4, 2);
        rval += "<tr><td class='method-byte-address'>" +
            Utils.formatAsFourByteHexString(startByteIndex + 4) +
            "</td><td class='method-byte-address' " +
            "<span style='" + style + "'>" +
            Utils.formatAsHexString(descriptorIndex) + "</span></td><td>" +
            "<span style='" + style + "'>" +
            "#" + descriptorIndex + " " + descriptor + "</span></td></tr>";

        style = PointSelector.getStyleForByte(startByteIndex + 6, 2);
        rval += "<tr><td class='method-byte-address'>" + Utils.formatAsFourByteHexString(startByteIndex + 6) +
            "</td><td class='method-byte-address'" +
            "<span style='" + style + "'>" +
            Utils.formatAsHexString(attributesCount) + "</span></td><td>" +
            "<span style='" + style + "'>" +
            "Attributes count: " + attributesCount + "</span></td></tr>"

            + "</table><br/>";

        for (MethodAttribute attribute : attributes) {
            rval += attribute.toString() + "<br/>";
        }

        rval +=
            "<table>" +// border=1>" +
                "<tr><td><span style='font-weight:" + ((accessFlags & 0x0001) != 0 ? "bold'" : "normal'") + ">ACC_PUBLIC      </span></td><td> 0x0001 </td><td>Declared public; may be accessed from outside its package.</td></tr>" +
                "<tr><td><span style='font-weight:" + ((accessFlags & 0x0002) != 0 ? "bold'" : "normal'") + ">ACC_PRIVATE     </span></td><td> 0x0002 </td><td>Declared private; accessible only within the defining class." +
                "<tr><td><span style='font-weight:" + ((accessFlags & 0x0004) != 0 ? "bold'" : "normal'") + ">ACC_PROTECTED   </span></td><td> 0x0004 </td><td>Declared protected; may be accessed within subclasses." +
                "<tr><td><span style='font-weight:" + ((accessFlags & 0x0008) != 0 ? "bold'" : "normal'") + ">ACC_STATIC      </span></td><td> 0x0008 </td><td>Declared static." +
                "<tr><td><span style='font-weight:" + ((accessFlags & 0x0010) != 0 ? "bold'" : "normal'") + ">ACC_FINAL       </span></td><td> 0x0010 </td><td>Declared final; must not be overridden (§5.4.5)." +
                "<tr><td><span style='font-weight:" + ((accessFlags & 0x0020) != 0 ? "bold'" : "normal'") + ">ACC_SYNCHRONIZED</span></td><td> 0x0040 </td><td>Declared synchronized; invocation is wrapped by a monitor use." +
                "<tr><td><span style='font-weight:" + ((accessFlags & 0x0040) != 0 ? "bold'" : "normal'") + ">ACC_BRIDGE      </span></td><td> 0x0080 </td><td>A bridge method, generated by the compiler." +
                "<tr><td><span style='font-weight:" + ((accessFlags & 0x0080) != 0 ? "bold'" : "normal'") + ">ACC_VARARGS     </span></td><td> 0x1000 </td><td>Declared with variable number of arguments." +
                "<tr><td><span style='font-weight:" + ((accessFlags & 0x0100) != 0 ? "bold'" : "normal'") + ">ACC_NATIVE      </span></td><td> 0x1000 </td><td>Declared native; implemented in a language other than Java." +
                "<tr><td><span style='font-weight:" + ((accessFlags & 0x0400) != 0 ? "bold'" : "normal'") + ">ACC_ABSTRACT    </span></td><td> 0x1000 </td><td>Declared abstract; no implementation is provided." +
                "<tr><td><span style='font-weight:" + ((accessFlags & 0x0800) != 0 ? "bold'" : "normal'") + ">ACC_STRICT      </span></td><td> 0x1000 </td><td>Declared strictfp; floating-point mode is FP-strict." +
                "<tr><td><span style='font-weight:" + ((accessFlags & 0x1000) != 0 ? "bold'" : "normal'") + ">ACC_SYNTHETIC   </span></td><td> 0x1000 </td><td>Declared synthetic; not present in the source code." +
                "</table><br/>";

        return rval;
    }
}
