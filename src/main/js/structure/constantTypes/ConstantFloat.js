function ConstantFloat (elementCountVar, byteArray, index) {
    var bytes = byteArray;
    var startByteIndex = index;
    var IEE754 = getFourBytes(bytes, index + 1);
    var value = convertToDecimal(IEE754);
    var signBit = extractSignBit(IEE754);
    var exponent = extractExponent(IEE754);
    var mantissa = extractMantissa(IEE754, exponent);
    var mantissaBytes = extractMantissaBytes(IEE754);
    // todo: move this explanation calc to IEEE754Converter.js
    var explanation = formatAsFourByteHexString(signBit) + " sign bit:" + signBit + "<br/>" +
        formatAsFourByteHexString(exponent+127) + " exponent:" + (exponent+127) + "-127=" + exponent +"<br/>" +
        formatAsFourByteHexString(mantissaBytes) + " mantissa:" + mantissa + "<br/><span class='tab'>&nbsp;</span>" +
        + value + " = " + (signBit == 1 ?"-1":"+1") + " * 2^" + exponent + " * " + mantissa
    ;


    /**
     * "tag item" is how the documentation refers to the type byte
     *
     * @return
     */
    this.getTag = function() {
        return 4;
    }

    /**
     * Index to the first byte of this section relative to the constant pool
     *
     * @return
     */
    this.getStartByteIndex = function() {
        return startByteIndex;
    }

    /**
     * length in bytes of this section
     *
     * @return length in bytes of this section
     */
    this.length = function() {
        return 5;
    }

    this.getEndByteIndex = function() {
        return this.getStartByteIndex() + this.length();
    }

    this.getCssClass = function() {
        return "float-section";
    }

    this.getBytecode = function () {
        return "Float <span class='tab'>&nbsp;</span> " + value + "</br>" + explanation;
    }

    this.getValue = function() {
        return value;
    }

    this.contains = function (index) {
        if(index >= this.getStartByteIndex() && index < this.getStartByteIndex() + this.length()) {
            return true;
        }
        else {
            return false;
        }
    }


}
