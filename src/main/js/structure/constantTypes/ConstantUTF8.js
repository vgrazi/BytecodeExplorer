function ConstantUTF8 (elementCountVar, byteArray, index) {
    var bytes = byteArray;
    var startByteIndex = index;
    var stringLength = getTwoBytes(bytes, index + 1);
    var stringValue= bin2String(bytes.slice(index + 3, index + 3 + stringLength)).replace(/</g, "&lt;");


    /**
     * "tag item" is how the documentation refers to the type byte
     *
     * @return
     */
    this.getTag = function() {
        return 1;
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
        return stringLength + 3;
    }

    this.getEndByteIndex = function() {
        return this.getStartByteIndex() + this.length();
    }

    this.getCssClass = function() {
        return "utf8-section";
    }

    this.getBytecode = function () {
        return "UTF8 <span class='tab'>&nbsp;</span> " + stringValue;
    }

    this.getStringValue = function() {
        return stringValue;
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
