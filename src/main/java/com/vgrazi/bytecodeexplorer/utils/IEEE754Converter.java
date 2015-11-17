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
Bit 10 to 31 are the mantisa,
let a(n) be the nth (beginning from n=1) byte (0 or 1) in the mantissa
Mantisa = 1 + sum(a(n)/(2^n))
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

        long mantissa = extractMantissa(value, exponent);
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

    public static long extractMantissa(long value, long exponent) {
        long mantissa = 0;
        long mantissaBits = value & 0x7fffff;
        int divisor = 2;
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

    public static String getExplanation(long IEE754, double value, byte signBit, long exponent, long mantissa, long mantissaBytes) {
        return
            Utils.formatAsFourByteHexString(IEE754) + " =<br/>" +
                "&nbsp;" + Utils.formatAsBinary(IEE754) + "=<br>" +
                IEEE754Converter.formatBinaryAsIEEE754(IEE754) + "<br>" +
                "<font color='blue'>" + Utils.formatAsFourByteHexString(signBit) + " sign bit:" + signBit + "</font><br/>" +
                "<font color='red'>" + Utils.formatAsFourByteHexString(exponent + 127) + " exponent:" + (exponent + 127) + "-127=" + exponent + "</font><br/>" +
                "<font color='green'>" + Utils.formatAsFourByteHexString(mantissaBytes) + " mantissa:" + mantissa + "</font><br/><span class='tab'>&nbsp;</span>" +
                +value + " = <font color='blue'>" + (signBit == 1 ? "-1" : "+1") + "</font> * 2^<font color='red'>" + exponent + "</font> * <font color='green'>" + mantissa + "</font>";
    }

    public static String formatBinaryAsIEEE754(long value) {
        String string = Long.toBinaryString(value);
        if (string.length() < 32) {
            string = "00000000000000000000000000000000".substring(string.length()) + string;
        }
        return
            "<font color='blue'>" + string.substring(0, 1) + "</font> " +
            "<font color='red'>" + string.substring(1, 5) + "</font> " +
            "<font color='red'>" + string.substring(5, 9) + "</font> " +
            "<font color='green'>" + string.substring(9, 12) + "</font> " +
            "<font color='green'>" + string.substring(12, 16) + "</font> " +
            "<font color='green'>" + string.substring(16, 20) + "</font> " +
            "<font color='green'>" + string.substring(20, 24) + "</font> " +
            "<font color='green'>" + string.substring(24, 28) + "</font> " +
            "<font color='green'>" + string.substring(28, 32) + "</font>";
    }
}
