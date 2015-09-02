package com.vgrazi.bytecodeexplorer;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vgrazi on 8/11/15.
 */
public class BytecodeExplorerFirstLook {

    // javap -c -s -verbose SampleClass.class
//     0..3: Magic: CAFEBABE
//     4..5: major version
//     6..7: minor version
//     8..9: number of constants in pool +1
//    ClassFile {
//        u4             magic;
//        u2             minor_version;
//        u2             major_version;
//        u2             constant_pool_count;
//        cp_info        constant_pool[constant_pool_count-1];
//        u2             access_flags;
//        u2             this_class;
//        u2             super_class;
//        u2             interfaces_count;
//        u2             interfaces[interfaces_count];
//        u2             fields_count;
//        field_info     fields[fields_count];
//        u2             methods_count;
//        method_info    methods[methods_count];
//        u2             attributes_count;
//        attribute_info attributes[attributes_count];
//    }
    private static final int COL_COUNT = 16;
    private static final String FORMAT = "%-14s";
    public static final String RIGHT_PAD = "%02X ";
    public static final String NO_RIGHT_PAD = "%02X";
    private static Map<String, String> map0 = new HashMap<>();
    private static Map<String, String> map1 = new HashMap<>();
    private static Map<String, String> map2 = new HashMap<>();

//00001280 00 21 00 0B 00 0C 00 00 00 00 00 02 00 01 00 0D 00 0E 00 01 --- --- --- --- --- --- --- --- --- --- --- ---
//00001300 00 0F 00 00 00 2F 00 01 00 01 00 00 00 05 2A B7 00 01 B1 00 --- --- --- --- --- --- --- --- --- invokespecial 00 01 ---
//00001320 00 00 02 00 10 00 00 00 06 00 01 00 00 00 09 00 11 00 00 00 --- --- --- bipush 00 --- --- --- --- --- --- --- sipush 00 00 ---
    public static void main(String[] args) throws IOException {
        new BytecodeExplorerFirstLook().launch(args[0]);
    }

    int auxiliaryByteCount = 0;
    private void launch(String file) throws IOException {
        initMap();
        byte[] bytes = Files.readAllBytes(Paths.get(file));
        // extract all indexes and determine bounds of constant pool
//        processConstantPool();
        int index = 0;

        for (int i = 0; /*i < 50*/; i += COL_COUNT) {
            System.out.printf("%8d   ", index);
            for (int j = 0; j < COL_COUNT; j++) {
                int index1 = i + j;
                if (index1 >= bytes.length) {
                    break;
                }

                byte b = bytes[index1];
                System.out.print(String.format(RIGHT_PAD, b));
            }
            System.out.print("  ");

            boolean rval = false;

            for (int j = 0; j < COL_COUNT; j++) {
                int index1 = i + j;
                if (index1 < bytes.length) {
                    byte aByte = bytes[index1];
                    if(index1 >= 0 && index1 < 4 ) {
                        //CAFEBABE
                        displayMagicNumber(aByte, index1 ==3);
                    }
                    else if(index1 >=4  && index1 < 6 ) {
                        // major
                        displayByte(aByte, index1!=4);
                    }
                    else if(index1 >=6  && index1 < 8 ) {
                        // minor
                        displayByte(aByte, index1 !=6);
                    }
                    else if(index1 >=8  && index1 < 10 ) {
                        // constant pool size+1
                        displayByte(aByte, index1 != 8);
//                    }
//                    else if (isInConstantPool(index1)) {
//                        displayConstant(index1);
                    } else {
                        displayInstruction(aByte);
                    }
                } else {
                    rval = true;
                }
            }
            if (rval) {
                break;
            }
            index += COL_COUNT;
            System.out.println();
        }
    }

    private void displayMagicNumber(byte aByte, boolean rightPad) {
        displayByte(aByte, rightPad);
    }

    private PrintStream displayByte(byte aByte, boolean rightPad) {
        String mask = rightPad ? RIGHT_PAD : NO_RIGHT_PAD;
        return System.out.printf(mask, aByte);
    }

    private void displayInstruction(byte b) {
        String key = String.format(NO_RIGHT_PAD, b);
        if (auxiliaryByteCount == 0) {
            String instruction = map0.get(key);
            if (instruction == null) {
                instruction = map1.get(key);
                if (instruction == null) {
                    instruction = map2.get(key);
                    if (instruction == null) {
                        instruction = "---";
                        System.out.printf(FORMAT, instruction + " ");
                        auxiliaryByteCount = 0;
                    } else {
                        System.out.printf(FORMAT, instruction + " ");
                        auxiliaryByteCount = 2;
                    }
                } else {
                    System.out.printf(FORMAT, instruction + " ");
                    auxiliaryByteCount = 1;
                }
            } else {
                System.out.printf(FORMAT, instruction + " ");
                auxiliaryByteCount = 0;
            }
        } else {
            System.out.printf(FORMAT, key + " ");
            auxiliaryByteCount--;
        }
    }

    private void initMap() {
        map0.put("0A", "lconst_1");
        map0.put("0B", "fconst_0");
        map0.put("0C", "fconst_1");
        map0.put("0D", "fconst_2");
        map0.put("0E", "dconst_0");
        map1.put("10", "bipush");
        map0.put("0F", "dconst_1");
        map0.put("01", "aconst_null");
        map2.put("11", "sipush");
        map1.put("12", "ldc");
        map2.put("13", "ldc_w");
        map2.put("14", "ldc2_w");
        map1.put("15", "iload");
        map1.put("16", "lload");
        map1.put("17", "fload");
        map1.put("18", "dload");
        map1.put("19", "aload");
        map0.put("1A", "iload_0");
        map0.put("1B", "iload_1");
        map0.put("1C", "iload_2");
        map0.put("1D", "iload_3");
        map0.put("1E", "lload_0");
        map0.put("1F", "lload_1");
        map0.put("20", "lload_2");
        map0.put("21", "lload_3");
        map0.put("22", "fload_0");
        map0.put("02", "iconst_m1");
        map0.put("23", "fload_1");
        map0.put("24", "fload_2");
        map0.put("25", "fload_3");
        map0.put("26", "dload_0");
        map0.put("27", "dload_1");
        map0.put("28", "dload_2");
        map0.put("29", "dload_3");
        map0.put("2A", "aload_0");
        map0.put("2B", "aload_1");
        map0.put("2C", "aload_2");
        map0.put("2D", "aload_3");
        map0.put("2E", "iaload");
        map0.put("30", "faload");
        map0.put("2F", "laload");
        map0.put("31", "daload");
        map0.put("32", "aaload");
        map0.put("33", "baload");
        map0.put("03", "iconst_0");
        map0.put("34", "caload");
        map0.put("35", "saload");
        map1.put("36", "istore");
        map1.put("37", "lstore");
        map1.put("38", "fstore");
        map1.put("39", "dstore");
        map1.put("3A", "astore");
        map0.put("3B", "istore_0");
        map0.put("3C", "istore_1");
        map0.put("3D", "istore_2");
        map0.put("3E", "istore_3");
        map0.put("3F", "lstore_0");
        map0.put("40", "lstore_1");
        map0.put("41", "lstore_2");
        map0.put("42", "lstore_3");
        map0.put("43", "fstore_0");
        map0.put("44", "fstore_1");
        map0.put("04", "iconst_1");
        map0.put("45", "fstore_2");
        map0.put("46", "fstore_3");
        map0.put("47", "dstore_0");
        map0.put("48", "dstore_1");
        map0.put("49", "dstore_2");
        map0.put("4A", "dstore_3");
        map0.put("4B", "astore_0");
        map0.put("4C", "astore_1");
        map0.put("4D", "astore_2");
        map0.put("4E", "astore_3");
        map0.put("4F", "iastore");
        map0.put("50", "lastore");
        map0.put("51", "fastore");
        map0.put("52", "dastore");
        map0.put("53", "aastore");
        map0.put("54", "bastore");
        map0.put("55", "castore");
        map0.put("05", "iconst_2");
        map0.put("56", "sastore");
        map0.put("57", "pop");
        map0.put("58", "pop2");
        map0.put("59", "dup");
        map0.put("5A", "dup_x1");
        map0.put("5B", "dup_x2");
        map0.put("5C", "dup2");
        map0.put("5D", "dup2_x1");
        map0.put("5E", "dup2_x2");
        map0.put("60", "iadd");
        map0.put("5F", "swap");
        map0.put("61", "ladd");
        map0.put("62", "fadd");
        map0.put("63", "dadd");
        map0.put("64", "isub");
        map0.put("65", "lsub");
        map0.put("66", "fsub");
        map0.put("06", "iconst_3");
        map0.put("67", "dsub");
        map0.put("68", "imul");
        map0.put("69", "lmul");
        map0.put("6A", "fmul");
        map0.put("6B", "dmul");
        map0.put("6C", "idiv");
        map0.put("6D", "ldiv");
        map0.put("6E", "fdiv");
        map0.put("6F", "ddiv");
        map0.put("70", "irem");
        map0.put("71", "lrem");
        map0.put("72", "frem");
        map0.put("73", "drem");
        map0.put("74", "ineg");
        map0.put("75", "lneg");
        map0.put("76", "fneg");
        map0.put("77", "dneg");
        map0.put("07", "iconst_4");
        map0.put("78", "ishl");
        map0.put("79", "lshl");
        map0.put("7A", "ishr");
        map0.put("7B", "lshr");
        map0.put("7C", "iushr");
        map0.put("7D", "lushr");
        map0.put("7E", "iand");
        map0.put("80", "ior");
        map0.put("7F", "land");
        map0.put("81", "lor");
        map0.put("82", "ixor");
        map0.put("83", "lxor");
        map2.put("84", "iinc");
        map0.put("85", "i2l");
        map0.put("86", "i2f");
        map0.put("87", "i2d");
        map0.put("08", "iconst_5");
        map0.put("88", "l2i");
        map0.put("89", "l2f");
        map0.put("8A", "l2d");
        map0.put("8B", "f2i");
        map0.put("8C", "f2l");
        map0.put("8D", "f2d");
        map0.put("8E", "d2i");
        map0.put("90", "d2f");
        map0.put("8F", "d2l");
        map0.put("91", "i2b");
        map0.put("92", "i2c");
        map0.put("93", "i2s");
        map0.put("94", "lcmp");
        map0.put("95", "fcmpl");
        map0.put("96", "fcmpg");
        map0.put("97", "dcmpl");
        map0.put("98", "dcmpg");
        map2.put("99", "ifeq");
        map0.put("09", "lconst_0");
        map2.put("9A", "ifne");
        map2.put("9B", "iflt");
        map2.put("9C", "ifge");
        map2.put("9D", "ifgt");
        map2.put("9E", "ifle");
        map2.put("9F", "if_icmpeq");
        map2.put("A0", "if_icmpne");
        map2.put("A1", "if_icmplt");
        map2.put("A2", "if_icmpge");
        map2.put("A3", "if_icmpgt");
        map2.put("A4", "if_icmple");
        map2.put("A5", "if_acmpeq");
        map2.put("A6", "if_acmpne");
        map2.put("A7", "goto");
        map2.put("A8", "jsr");
        map1.put("A9", "ret");
        map0.put("AA", "tableswitch");
        map0.put("AB", "lookupswitch");
        map0.put("AC", "ireturn");
        map0.put("AD", "lreturn");
        map0.put("AE", "freturn");
        map0.put("B0", "areturn");
        map0.put("AF", "dreturn");
        map0.put("B1", "return");
        map2.put("B2", "getstatic");
        map2.put("B3", "putstatic");
        map2.put("B4", "getfield");
        map2.put("B5", "putfield");
        map2.put("B6", "virtual");
        map2.put("B7", "invokespecial");
        map2.put("B8", "invokestatic");
        map0.put("B9", "invokeinterface");
        map0.put("BA", "invokedynamic");
        map2.put("BB", "new");
        map1.put("BC", "newarray");
        map2.put("BD", "anewarray");
        map0.put("BE", "arraylength");
        map0.put("BF", "athrow");
        map2.put("C0", "checkcast");
        map2.put("C1", "instanceof");
        map0.put("C2", "monitorenter");
        map0.put("C3", "monitorexit");
        map0.put("C4", "wide");
        map0.put("C5", "multianewarray");
        map2.put("C6", "ifnull");
        map2.put("C7", "ifnonnull");
        map0.put("C8", "goto_w");
        map0.put("C9", "jsr_w");
        map0.put("CA", "breakpoint");
        map0.put("FE", "impdep1");
        map0.put("FF", "impdep2");
        map0.put("", "nop");



    }
}