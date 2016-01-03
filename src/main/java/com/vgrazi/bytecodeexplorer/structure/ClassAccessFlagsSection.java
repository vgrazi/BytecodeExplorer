package com.vgrazi.bytecodeexplorer.structure;

import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 11/22/15.
 */
public class ClassAccessFlagsSection implements ClassFileSection {
    private int accessFlags;
    private int startByte;

    public ClassAccessFlagsSection(byte[] bytes, int startByte) {

        this.startByte = startByte;
        accessFlags = Utils.getTwoBytes(bytes, getStartByteIndex());


    }

    @Override
    public int length() {
        return 2;
    }

    @Override
    public int getStartByteIndex() {
        return startByte;
    }

    public String toString() {
        String rval = "Class Access flags: " + accessFlags + ": 0x" + Utils.formatAsHexString(accessFlags)+ ": " + Utils.formatAsBinary(accessFlags) + "<table align='left'>" +
              "<tr><td><span style='font-weight:"+((accessFlags & 0x0001) != 0 ? "bold'" : "normal'") + ">ACC_PUBLIC</span></td><td> 0x0001 </td><td>Declared public; may be accessed from outside its package.</td></tr>"
            + "<tr><td><span style='font-weight:"+((accessFlags & 0x0010) != 0 ? "bold'" : "normal'") + ">ACC_FINAL</span></td><td> 0x0010 </td><td>Declared final; no subclasses allowed.</td></tr>"
            + "<tr><td><span style='font-weight:"+((accessFlags & 0x0020) != 0 ? "bold'" : "normal'") + ">ACC_SUPER</span></td><td> 0x0020 </td><td>Treat superclass methods specially when invoked by the invokespecial instruction.</td></tr>"
            + "<tr><td><span style='font-weight:"+((accessFlags & 0x0200) != 0 ? "bold'" : "normal'") + ">ACC_INTERFACE</span></td><td> 0x0200 </td><td>Is an interface, not a class.</td></tr>"
            + "<tr><td><span style='font-weight:"+((accessFlags & 0x0400) != 0 ? "bold'" : "normal'") + ">ACC_ABSTRACT</span></td><td> 0x0400 </td><td>Declared abstract; must not be instantiated.</td></tr>"
            + "<tr><td><span style='font-weight:"+((accessFlags & 0x1000) != 0 ? "bold'" : "normal'") + ">ACC_SYNTHETIC</span></td><td> 0x1000 </td><td>Declared synthetic; not present in the source code.</td></tr>"
            + "<tr><td><span style='font-weight:"+((accessFlags & 0x2000) != 0 ? "bold'" : "normal'") + ">ACC_ANNOTATION</span></td><td> 0x2000 </td><td>Declared as an annotation type.</td></tr>"
            + "<tr><td><span style='font-weight:"+((accessFlags & 0x4000) != 0 ? "bold'" : "normal'") + ">ACC_ENUM</span></td><td> 0x4000 </td><td>Declared as an enum type.</td></tr>";
        return rval;
    }

}
