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
function convertToDecimal(value) {
    if(value == 0) {
        return 0;
    }
    else if(value == 0x7f800000) {
        return Infinity;
    }
    else if(value == 0xff800000) {
        return -Infinity;
    }
    else if(value == 0x7fffffff) {
        return NaN;
    }
    else if(value == 0xffffffff) {
        return -NaN;
    }
    var signBit = extractSignBit(value);

    var exponent = extractExponent(value);

    var mantissa = extractMantissa(value, exponent);
    var rval = Math.pow(2,exponent) * mantissa;

    if(signBit == 1) {
        rval = - rval;
    }
    return rval;
}

function extractSignBit(value) {
    var signBit = (value >> 31) & 0x01;
    return signBit;
}

function extractExponent(value) {
    var exponent = value >> 23;
    exponent = exponent & 0xFF; // remove the sign bit then subtract 127
    exponent -= 127;
    return exponent;
}

function extractMantissa(value, exponent) {
    var mantissa = 0
    var mantissaBits = value & 0x7fffff
    var divisor = 2;
    for(var i = 22; i >=0; i--) {
        var nextBit = (mantissaBits >> i) & 0x1;
        mantissa += nextBit/divisor;
        divisor *= 2;
    }
    if(exponent != -127) {
        // normalized form
        mantissa += 1;
    }
    else {
        // denormalized form
        mantissa *=2;
    }
    return mantissa;
}

function extractMantissaBytes(value) {
    var mantissa = value & 0x007fffff;
    return mantissa;
}
