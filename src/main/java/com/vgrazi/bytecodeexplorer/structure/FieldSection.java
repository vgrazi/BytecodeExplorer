package com.vgrazi.bytecodeexplorer.structure;

//field_info
//    u2             accessFlags;
//    u2             nameIndex;
//    u2             descriptorIndex;
//    u2             attributesCount;
//    attribute_info attributes[attributesCount];

import com.vgrazi.bytecodeexplorer.structure.constantTypes.ConstantType;
import com.vgrazi.bytecodeexplorer.utils.Utils;

import java.util.List;

/**
 * Created by vgrazi on 11/29/15.
 */
public class FieldSection implements ClassFileSection {

    private final int startByte;
    private final int accessFlags;
    private final int nameIndex;
    private final int descriptorIndex;
    private final int attributesCount;
    private final byte[] attributes;


    public FieldSection(byte[] bytes, int startByte) {

        this.startByte = startByte;
        this.accessFlags = Utils.getTwoBytes(bytes, startByte);
        this.nameIndex = Utils.getTwoBytes(bytes, startByte + 2);
        this.descriptorIndex = Utils.getTwoBytes(bytes, startByte + 4);
        this.attributesCount = Utils.getTwoBytes(bytes, startByte + 6);
        this.attributes = new byte[attributesCount];
        System.arraycopy(bytes, startByte + 8, attributes, 0, attributes.length);
    }

    /**
     * length in bytes of this section
     *
     * @return length in bytes of this section
     */
    @Override
    public int length() {
        return attributes.length + 8;
    }

    /**
     * Index to the first byte of this section relative to the constant pool
     */
    @Override
    public int getStartByteIndex() {
        return startByte;
    }

    @Override
    public String toString() {
        List<ConstantType> constants = ConstantPoolSection.getConstants();
        StringBuilder sb = new StringBuilder("Field:<br/><table>");
        String name = constants.get(nameIndex - 1).toString();
        String descriptor = constants.get(descriptorIndex - 1).toString();
        sb.append("<tr><td>" + Utils.formatAsTwoByteHexString(accessFlags) +     "</td><td> Access flags:</td><td>" + Utils.formatAsBinary(accessFlags) + "</td></tr>");
        sb.append("<tr><td>" + Utils.formatAsTwoByteHexString(nameIndex) +       "</td><td> Name:</td><td>" + name + "</td></tr>");
        sb.append("<tr><td>" + Utils.formatAsTwoByteHexString(descriptorIndex) + "</td><td> Descriptor:</td><td>" + descriptor + "</td></tr>");
        sb.append("<tr><td>" + Utils.formatAsTwoByteHexString(attributesCount) + "</td><td> Attributes Count:</td><td>" + String.valueOf(attributes.length) + "</td></tr></table><br/>");

        sb.append("<table>" +
            "<tr><td width=20>&nbsp;</td><td width=130> " + (((accessFlags & 0x0001) != 0) ? "<strong>" : "") + "ACC_PUBLIC	    </td><td>0x0001</td><td>Declared public; may be accessed from outside its package.</td></tr>" +
            "<tr><td width=20>&nbsp;</td><td width=130> " + (((accessFlags & 0x0002) != 0) ? "<strong>" : "") + "ACC_PRIVATE	    </td><td>0x0002</td><td>Declared private; usable only within the defining class.</td></tr>" +
            "<tr><td width=20>&nbsp;</td><td width=130> " + (((accessFlags & 0x0004) != 0) ? "<strong>" : "") + "ACC_PROTECTED	</td><td>0x0004</td><td>Declared protected; may be accessed within subclasses.</td></tr>" +
            "<tr><td width=20>&nbsp;</td><td width=130> " + (((accessFlags & 0x0008) != 0) ? "<strong>" : "") + "ACC_STATIC	    </td><td>0x0008</td><td>Declared static.</td></tr>" +
            "<tr><td width=20>&nbsp;</td><td width=130> " + (((accessFlags & 0x0010) != 0) ? "<strong>" : "") + "ACC_FINAL   	    </td><td>0x0010</td><td>Declared final; never directly assigned to after object construction (JLS §17.5).</td></tr>" +
            "<tr><td width=20>&nbsp;</td><td width=130> " + (((accessFlags & 0x0040) != 0) ? "<strong>" : "") + "ACC_VOLATILE	    </td><td>0x0040</td><td>Declared volatile; cannot be cached.</td></tr>" +
            "<tr><td width=20>&nbsp;</td><td width=130> " + (((accessFlags & 0x0080) != 0) ? "<strong>" : "") + "ACC_TRANSIENT	</td><td>0x0080</td><td>Declared transient; not written or read by a persistent object manager.</td></tr>" +
            "<tr><td width=20>&nbsp;</td><td width=130> " + (((accessFlags & 0x1000) != 0) ? "<strong>" : "") + "ACC_SYNTHETIC	</td><td>0x1000</td><td>Declared synthetic; not present in the source code.</td></tr>" +
            "</table><br/>");

        return sb.toString();
    }
}
