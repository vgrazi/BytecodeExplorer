package com.vgrazi.bytecodeexplorer.utils;

import com.vgrazi.bytecodeexplorer.structure.ConstantPoolSection;
import com.vgrazi.bytecodeexplorer.structure.constantTypes.ConstantType;
import com.vgrazi.bytecodeexplorer.structure.constantTypes.ConstantUTF8;
import com.vgrazi.bytecodeexplorer.ui.swing.PointSelector;

import java.util.List;

/**
 * Created by vgrazi on 8/13/15.
 */
public class Utils {
    public static int getFourBytes(byte[] bytes, int index) {
        int value =
            (int) ((((int) bytes[index++] << 24) & 0xff000000L) |
                            (((int) bytes[index++] << 16) & 0xff0000L) |
                            (((int) bytes[index++] << 8) & 0xff00L) |
                            ((int) bytes[index++]) & 0xffL);

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

    public static String formatAsHexString(int value) {
        return String.format("%02X", value);
    }

    public static String formatAsOneByteHexString(int value) {
        String format = String.format("%02X", value);
        return format.substring(format.length()-2);
    }

    public static String formatAsTwoByteHexString(int value) {
        String format = String.format("%04X", value);
        return format.substring(0,2) + " " + format.substring(2);
    }

    public static String formatAsFourByteHexString(long value) {
        return String.format("%04X", value);
    }

    public static String formatAsBinary(int value) {
        String string = Integer.toBinaryString(value);
        if (string.length() < 16) {
            string = "0000000000000000".substring(string.length()) + string;
        }
        return
            string.substring(0, 4) + " " +
            string.substring(4, 8) + " " +
            string.substring(8, 12) + " " +
            string.substring(12, 16);
    }

    /**
     * Returns the number of bytes specified from the supplied array formatted as comma delimited hex
     * @param bytesArray
     * @param index
     * @param length
     * @return
     */
    public static String formatAsHexStringFromArray(byte[] bytesArray, int index, int length) {
        StringBuilder rval = new StringBuilder();
        for(int i = 0; i < length; i++) {
            byte bite = bytesArray[index + i];
            if(i > 0) {
                rval.append(",");
            }
            rval.append(formatAsOneByteHexString(bite));
        }
        return rval.toString();
    }

    public static String formatAs32BitSectionedBinary(long value) {
        String string = formatAs32BitBinary(value);
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

    public static String formatAs32BitBinary(long value) {
        String string = Long.toBinaryString(value);
        if (string.length() < 32) {
            string = "00000000000000000000000000000000".substring(string.length()) + string;
        }
        return string;
    }

    public static String getAddress(int index) {
        String style = "";
        if (index == PointSelector.getMouseByteIndex()) {
            style = "style='font-weight:bold'";
        }
        return "<span " + style + ">" + Utils.formatAsFourByteHexString(index) + "</span>";
    }


    public static String getDirectString(int index) {
        List<ConstantType> constants = ConstantPoolSection.getConstants();
        ConstantUTF8 utfConstant = (ConstantUTF8) constants.get(index - 1);
        String stringValue = escape(utfConstant.getStringValue());
        return stringValue;
    }

    private static String escape(String stringValue) {
        return(stringValue.replaceAll("\\<", "&lt;"));
    }

//    public static String getIndirectString(int index) {
//        List<ConstantType> constants = ConstantPoolSection.getConstants();
//        // index points to a constant. Extract that, then find where it points
//        ConstantType constant = constants.get(index -1);
//        var utfIndex = constant.getReferenceIndex();
//        return getDirectString(utfIndex);
//    }

//    function getText(bytes, index) {
//        var type = getTwoBytes(bytes, index);
//        var length = getTwoBytes(bytes, index + 2);
//        var array = bytes.slice(index + 4, index + 4 + length);
//        return bin2String(array);
//
//    }
}
