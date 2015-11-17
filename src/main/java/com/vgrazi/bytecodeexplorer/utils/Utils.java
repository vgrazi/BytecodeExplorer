package com.vgrazi.bytecodeexplorer.utils;

import com.vgrazi.bytecodeexplorer.structure.constantTypes.ConstantType;

import java.util.List;

/**
 * Created by vgrazi on 8/13/15.
 */
public class Utils {
    public static long getFourBytes(byte[] bytes, int index) {
        long value =
            (((long) bytes[index++] << 24) & 0xff000000L) |
                (((long) bytes[index++] << 16) & 0xff0000L) |
                (((long) bytes[index++] << 8) & 0xff00L) |
                ((long) bytes[index++]) & 0xffL;

        return value;
    }

    public static int getTwoBytes(byte[] bytes, int index) {
        int value =
            (((int) bytes[index++] << 8) & 0xff00) |
                ((int) bytes[index++]) & 0xff;

        return value;
    }

    public static void printConstants(List<ConstantType> constants) {
        for (int i = 0; i < constants.size(); i++) {
            ConstantType constantType = constants.get(i);
            System.out.printf("#%2d %2d %s%n",
                (i + 1), constantType.getTag(), constantType);
        }

    }

    public static String formatAsTwoByteHexString(long value) {
        return String.format("%02X", value);
    }

    public static String formatAsFourByteHexString(long value) {
        return String.format("%04X", value);
    }

    public static String formatAsBinary(long value) {
        String string = Long.toBinaryString(value);
        if (string.length() < 32) {
            string = "00000000000000000000000000000000".substring(string.length()) + string;
        }
        return
            string.substring(0, 4) + " " +
            string.substring(4, 8) + " " +
            string.substring(8, 12) + " " +
            string.substring(12, 16) + " " +
            string.substring(16, 20) + " " +
            string.substring(20, 24) + " " +
            string.substring(24, 28) + " " +
            string.substring(28, 32);
    }
}
