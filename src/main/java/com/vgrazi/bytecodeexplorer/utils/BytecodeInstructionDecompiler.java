package com.vgrazi.bytecodeexplorer.utils;

import com.vgrazi.bytecodeexplorer.ui.swing.PointSelector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vgrazi on 1/5/16
 */
public class BytecodeInstructionDecompiler {
    private final static Map<Byte, String> map = new HashMap<>();
    private final static Map<Byte, String> map1 = new HashMap<>();
    private final static Map<Byte, String> map2 = new HashMap<>();
    private final static Map<Byte, String> map3 = new HashMap<>();
    private final static Map<Byte, String> map4 = new HashMap<>();

    static {
        populateInstructionMaps();
    }

    public static String decompile(int startIndex, byte[] bytes, int byteCount) {
    StringBuilder instructions = new StringBuilder();
    for(int i = 0; i < byteCount; ) {
        int index = startIndex + i;
        byte bite = bytes[index];
        String instruction;
        int increment;
        // careful, this is = not ==, we are assigning and checking for undefined
        if((instruction = map.get(bite)) != null) {
            instructions.append(getCodeString(i, bite, instruction, bytes, index + 1, 0));
            increment = 1;
        }
        else if((instruction = map1.get(bite)) != null) {
            instructions.append(getCodeString(i, bite, instruction, bytes, index + 1, 1));
            increment = 2;
        }
        else if((instruction = map2.get(bite)) != null) {
            instructions.append(getCodeString(i, bite, instruction, bytes, index + 1, 2));
            increment = 3;
        }
        else if((instruction = map3.get(bite)) != null) {
            instructions.append(getCodeString(i, bite, instruction, bytes, index + 1, 3));
            increment = 4;
        }
        else if((instruction = map4.get(bite)) != null) {
            instructions.append(getCodeString(i, bite, instruction, bytes, index + 1, 4));
            increment = 5;
        }
        else {
            throw new Error("Unknown bytecode instruction code " + Utils.formatAsHexString(bite));
        }
        i+= increment;
    }

    return instructions.toString();
}

private static String getCodeString(int lineNumber, byte bite, String instruction, byte[] bytes, int index, int bytecount) {
    String style = PointSelector.getStyleForByte(index - 1, 1);
    String rval = "<tr>" +
        "<td>" + Utils.formatAsFourByteHexString(index - 1) + "</td>" +
        "<td style='width:30px;' class='decompile-row'>" + lineNumber + ":</td>" +
        "<td  class='decompile-bite'>" +
        "<span style='" + style + "'>" +
        Utils.formatAsOneByteHexString(bite) + "</span></td>" +
        "<td class='decompile-instruction'>" +
        "<span style='" + style + "'>" + instruction + "</span></td>";


    rval += "<td  class='decompile-args'>";
    for (int i = 0; i < bytecount; i++) {
        style = PointSelector.getStyleForByte(index + i, 1);
        rval += "<span style='" + style + "'>" + Utils.formatAsOneByteHexString(Utils.getOneByte(bytes, index + i)) + "</span></td>";
    }
    rval += "</tr>";

    return rval;
}

    private static void populateInstructionMaps() {
        map.put((byte) 0x01, "aconst_null");
        map.put((byte) 0x02, "iconst_m1");
        map.put((byte) 0x03, "iconst_0");
        map.put((byte) 0x04, "iconst_1");
        map.put((byte) 0x05, "iconst_2");
        map.put((byte) 0x06, "iconst_3");
        map.put((byte) 0x07, "iconst_4");
        map.put((byte) 0x08, "iconst_5");
        map.put((byte) 0x09, "lconst_0");
        map.put((byte) 0x0A, "lconst_1");
        map.put((byte) 0x0B, "fconst_0");
        map.put((byte) 0x0C, "fconst_1");
        map.put((byte) 0x0D, "fconst_2");
        map.put((byte) 0x0E, "dconst_0");
        map.put((byte) 0x0F, "dconst_1");
        map1.put((byte) 0x10, "bipush");
        map2.put((byte) 0x11, "sipush");
        map1.put((byte) 0x12, "ldc");
        map2.put((byte) 0x13, "ldc_w");
        map2.put((byte) 0x14, "ldc2_w");
        map1.put((byte) 0x15, "iload");
        map1.put((byte) 0x16, "lload");
        map1.put((byte) 0x17, "fload");
        map1.put((byte) 0x18, "dload");
        map1.put((byte) 0x19, "aload");
        map.put((byte) 0x1A, "iload_0");
        map.put((byte) 0x1B, "iload_1");
        map.put((byte) 0x1C, "iload_2");
        map.put((byte) 0x1D, "iload_3");
        map.put((byte) 0x1E, "lload_0");
        map.put((byte) 0x1F, "lload_1");
        map.put((byte) 0x20, "lload_2");
        map.put((byte) 0x21, "lload_3");
        map.put((byte) 0x22, "fload_0");
        map.put((byte) 0x23, "fload_1");
        map.put((byte) 0x24, "fload_2");
        map.put((byte) 0x25, "fload_3");
        map.put((byte) 0x26, "dload_0");

        map.put((byte)0x27, "dload_1");
        map.put((byte)0x28, "dload_2");
        map.put((byte)0x29, "dload_3");
        map.put((byte)0x2A, "aload_0");
        map.put((byte)0x2B, "aload_1");
        map.put((byte)0x2C, "aload_2");
        map.put((byte)0x2D, "aload_3");
        map.put((byte)0x2E, "iaload");
        map.put((byte)0x2F, "laload");
        map.put((byte)0x30, "faload");
        map.put((byte)0x31, "daload");
        map.put((byte)0x32, "aaload");
        map.put((byte)0x33, "baload");
        map.put((byte)0x34, "caload");
        map.put((byte)0x35, "saload");
        map1.put((byte)0x36, "istore");
        map1.put((byte)0x37, "lstore");
        map1.put((byte)0x38, "fstore");
        map1.put((byte)0x39, "dstore");
        map1.put((byte)0x3A, "astore");
        map.put((byte)0x3B, "istore_0");
        map.put((byte)0x3C, "istore_1");
        map.put((byte)0x3D, "istore_2");
        map.put((byte)0x3E, "istore_3");
        map.put((byte)0x3F, "lstore_0");
        map.put((byte)0x40, "lstore_1");
        map.put((byte)0x41, "lstore_2");
        map.put((byte)0x42, "lstore_3");
        map.put((byte)0x43, "fstore_0");
        map.put((byte)0x44, "fstore_1");
        map.put((byte)0x45, "fstore_2");
        map.put((byte)0x46, "fstore_3");
        map.put((byte)0x47, "dstore_0");
        map.put((byte)0x48, "dstore_1");
        map.put((byte)0x49, "dstore_2");
        map.put((byte)0x4A, "dstore_3");
        map.put((byte)0x4B, "astore_0");
        map.put((byte)0x4C, "astore_1");
        map.put((byte)0x4D, "astore_2");
        map.put((byte)0x4E, "astore_3");
        map.put((byte)0x4F, "iastore");
        map.put((byte)0x50, "lastore");
        map.put((byte)0x51, "fastore");
        map.put((byte)0x52, "dastore");
        map.put((byte)0x53, "aastore");
        map.put((byte)0x54, "bastore");
        map.put((byte)0x55, "castore");
        map.put((byte)0x56, "sastore");
        map.put((byte)0x57, "pop");
        map.put((byte)0x58, "pop2");
        map.put((byte)0x59, "dup");
        map.put((byte)0x5A, "dup_x1");
        map.put((byte)0x5B, "dup_x2");
        map.put((byte)0x5C, "dup2");
        map.put((byte)0x5D, "dup2_x1");
        map.put((byte)0x5E, "dup2_x2");
        map.put((byte)0x5F, "swap");
        map.put((byte)0x60, "iadd");
        map.put((byte)0x61, "ladd");
        map.put((byte)0x62, "fadd");
        map.put((byte)0x63, "dadd");
        map.put((byte)0x64, "isub");
        map.put((byte)0x65, "lsub");
        map.put((byte)0x66, "fsub");
        map.put((byte)0x67, "dsub");
        map.put((byte)0x68, "imul");
        map.put((byte)0x69, "lmul");
        map.put((byte)0x6A, "fmul");
        map.put((byte)0x6B, "dmul");
        map.put((byte)0x6C, "idiv");
        map.put((byte)0x6D, "ldiv");
        map.put((byte)0x6E, "fdiv");
        map.put((byte)0x6F, "ddiv");
        map.put((byte)0x70, "irem");
        map.put((byte)0x71, "lrem");
        map.put((byte)0x72, "frem");
        map.put((byte)0x73, "drem");
        map.put((byte)0x74, "ineg");
        map.put((byte)0x75, "lneg");
        map.put((byte)0x76, "fneg");
        map.put((byte)0x77, "dneg");
        map.put((byte)0x78, "ishl");
        map.put((byte)0x79, "lshl");
        map.put((byte)0x7A, "ishr");
        map.put((byte)0x7B, "lshr");
        map.put((byte)0x7C, "iushr");
        map.put((byte)0x7D, "lushr");
        map.put((byte)0x7E, "iand");
        map.put((byte)0x7F, "land");
        map.put((byte)0x80, "ior");
        map.put((byte)0x81, "lor");
        map.put((byte)0x82, "ixor");
        map.put((byte)0x83, "lxor");
        map2.put((byte)0x84, "iinc");
        map.put((byte)0x85, "i2l");
        map.put((byte)0x86, "i2f");
        map.put((byte)0x87, "i2d");
        map.put((byte)0x88, "l2i");
        map.put((byte)0x89, "l2f");
        map.put((byte)0x8A, "l2d");
        map.put((byte)0x8B, "f2i");
        map.put((byte)0x8C, "f2l");
        map.put((byte)0x8D, "f2d");
        map.put((byte)0x8E, "d2i");
        map.put((byte)0x8F, "d2l");
        map.put((byte)0x90, "d2f");
        map.put((byte)0x91, "i2b");
        map.put((byte)0x92, "i2c");
        map.put((byte)0x93, "i2s");
        map.put((byte)0x94, "lcmp");
        map.put((byte)0x95, "fcmpl");
        map.put((byte)0x96, "fcmpg");
        map.put((byte)0x97, "dcmpl");
        map.put((byte)0x98, "dcmpg");
        map2.put((byte)0x99, "ifeq");
        map2.put((byte)0x9A, "ifne");
        map2.put((byte)0x9B, "iflt");
        map2.put((byte)0x9C, "ifge");
        map2.put((byte)0x9D, "ifgt");
        map2.put((byte)0x9E, "ifle");
        map2.put((byte)0x9F, "if_icmpeq");
        map2.put((byte)0xA0, "if_icmpne");
        map2.put((byte)0xA1, "if_icmplt");
        map2.put((byte)0xA2, "if_icmpge");
        map2.put((byte)0xA3, "if_icmpgt");
        map2.put((byte)0xA4, "if_icmple");
        map2.put((byte)0xA5, "if_acmpeq");
        map2.put((byte)0xA6, "if_acmpne");
        map2.put((byte)0xA7, "goto");
        map2.put((byte)0xA8, "jsr");
        map1.put((byte)0xA9, "ret");
//    map4.put((byte)0xAA, "tableswitch");
//    map4.put((byte)0xAB, "lookupswitch");
        map.put((byte)0xAC, "ireturn");
        map.put((byte)0xAD, "lreturn");
        map.put((byte)0xAE, "freturn");
        map.put((byte)0xAF, "dreturn");
        map.put((byte)0xB0, "areturn");
        map.put((byte)0xB1, "return");
        map2.put((byte)0xB2, "getstatic");
        map2.put((byte)0xB3, "putstatic");
        map2.put((byte)0xB4, "getfield");
        map2.put((byte)0xB5, "putfield");
        map2.put((byte)0xB6, "invokevirtual");
        map2.put((byte)0xB7, "invokespecial");
        map2.put((byte)0xB8, "invokestatic");
        map4.put((byte)0xB9, "invokeinterface");
        map4.put((byte)0xBA, "invokedynamic");
        map2.put((byte)0xBB, "new");
        map1.put((byte)0xBC, "newarray");
        map2.put((byte)0xBD, "anewarray");
        map.put((byte)0xBE, "arraylength");
        map.put((byte)0xBF, "athrow");
        map2.put((byte)0xC0, "checkcast");
        map2.put((byte)0xC1, "instanceof");
        map.put((byte)0xC2, "monitorenter");
        map.put((byte)0xC3, "monitorexit");
//    mapap42068.put((byte)0xC4, "wide");
        map3.put((byte)0xC5, "multianewarray");
        map2.put((byte)0xC6, "ifnull");
        map2.put((byte)0xC7, "ifnonnull");
        map4.put((byte)0xC8, "goto_w");
        map4.put((byte)0xC9, "jsr_w");
        map.put((byte)0xCA, "breakpoint");
        map.put((byte)0xFD, "(no name)");
        map.put((byte)0xFE, "impdep1");
        map.put((byte)0xFF, "impdep2");
    }
}
