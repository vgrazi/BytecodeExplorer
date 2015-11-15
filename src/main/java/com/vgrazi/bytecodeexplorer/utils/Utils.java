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
            System.out.printf("#%2d %s%n",
                (i + 1),
                constantType.toString(constants));
        }

    }

    public static String formatAsFourByteHexString(long value) {
        return String.format("%04X", value);
    }
}
