package com.vgrazi.bytecodeexplorer.utils;

/**
 * Created by vgrazi on 11/14/15.
 */
public class IEEE754Converter {
    /*References:
 http://www.h-schmidt.net/FloatConverter/IEEE754.html
 http://steve.hollasch.net/cgindex/coding/ieeefloat.html

32 bits:
Special cases:
 0        = 0x0
 Infinity = 0x7f800000 (exponent all 1, mantisa all 0)
-Infinity = 0xff800000 (exponent all 1, mantisa all 0, sign bit=1)
 NaN      = 0x7fffffff
-NaN      = 0xffffffff
 Otherwise:
Bit  1 is the sign bit (0="+" 1="-")
Bits 2 to 9 are the exponent (first subtract 127)
Bit 10 to 31 are the mantissa,
let a(n) be the nth (beginning from n=1) byte (0 or 1) in the mantissa
Mantissa = 1 + sum(a(n)/(2^n))
*/
    public static double convertToDecimal(long value) {
        if (value == 0) {
            return 0;
        } else if (value == 0x7f800000) {
            return Double.POSITIVE_INFINITY;
        } else if (value == 0xff800000) {
            return Double.NEGATIVE_INFINITY;
        } else if (value == 0x7fffffff) {
            return Double.NaN;
        } else if (value == 0xffffffff) {
            return -Double.NaN;
        }
        byte signBit = extractSignBit(value);

        long exponent = extractExponent(value);

        double mantissa = extractMantissa(value, exponent);
        double rval = Math.pow(2, exponent) * mantissa;

        if (signBit == 1) {
            rval = -rval;
        }
        return rval;
    }

    public static byte extractSignBit(long value) {
        byte signBit = (byte) ((value >> 31) & 0x01);
        return signBit;
    }

    public static long extractExponent(long value) {
        long exponent = value >> 23;
        exponent = exponent & 0xFF; // remove the sign bit then subtract 127
        exponent -= 127;
        return exponent;
    }

    public static double extractMantissa(long value, long exponent) {
        double mantissa = 0;
        long mantissaBits = value & 0x7fffff;
        float divisor = 2;
        for (int i = 22; i >= 0; i--) {
            int nextBit = (int) ((mantissaBits >> i) & 0x1);
            mantissa += nextBit / divisor;
            divisor *= 2;
        }
        if (exponent != -127) {
            // normalized form
            mantissa += 1;
        } else {
            // denormalized form
            mantissa *= 2;
        }
        return mantissa;
    }

    public static long extractMantissaBytes(long value) {
        long mantissa = value & 0x007fffff;
        return mantissa;
    }

    public static String getExplanation(int IEE754, double value, byte signBit, long exponent, double mantissa, long mantissaBytes) {
        String IEE754HexString = Utils.formatAsHexString(IEE754);
        String IEE754BinaryString = Utils.formatAs32BitSectionedBinary(IEE754);
        String IEEE754Regrouped = IEEE754Converter.formatBinaryAsIEEE754(IEE754);
        String exponentString = Utils.formatAsFourByteHexString(exponent + 127);
        String mantissaString = Utils.formatAsFourByteHexString(mantissaBytes);
        return
            IEE754HexString + " =<br/>" +
                "&nbsp;" + IEE754BinaryString + "=<br>" +
                IEEE754Regrouped + "<br>" +
                "<font color='blue'>" + signBit + " sign bit:" + signBit + "</font><br/>" +
                "<font color='red'>" + exponentString + " exponent:" + (exponent + 127) + "-127=" + exponent + "</font><br/>" +
                "<font color='green'>" + mantissaString + " mantissa:" + mantissa + "</font><br/><span class='tab'>&nbsp;</span>" +
                +value + " = <font color='blue'>" + (signBit == 1 ? "-1" : "+1") + "</font> * 2^<font color='red'>" + exponent + "</font> * <font color='green'>" + mantissa + "</font>";
    }

    public static String formatBinaryAsIEEE754(int iee754) {
        String string = Utils.formatAs32BitBinary(iee754);
        String signBit = string.substring(0, 1);
        String exponent1 = string.substring(1, 5);
        String exponent2 = string.substring(5, 9);
        String mantissa1 = string.substring(9, 12);
        String mantissa2 = string.substring(12, 16);
        String mantissa3 = string.substring(16, 20);
        String mantissa4 = string.substring(20, 24);
        String mantissa5 = string.substring(24, 28);
        String mantissa6 = string.substring(28, 32);
        return
            "<font color='blue'>" + signBit + "</font> " +
            "<font color='red'>" + exponent1 + "</font> " +
            "<font color='red'>" + exponent2 + "</font> " +
            "<font color='green'>" + mantissa1 + "</font> " +
            "<font color='green'>" + mantissa2 + "</font> " +
            "<font color='green'>" + mantissa3 + "</font> " +
            "<font color='green'>" + mantissa4 + "</font> " +
            "<font color='green'>" + mantissa5 + "</font> " +
            "<font color='green'>" + mantissa6 + "</font>";
    }
}

