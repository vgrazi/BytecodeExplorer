function ConstantString (elementCountVar, bytes, index) {

    var startByteIndex = index;
    var stringindex = getTwoBytes(bytes, index + 1);

    var length;

    var elementCount = elementCountVar;
    var constants = [];

    this.getTag = function() {
        return 8;
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
        return 3;
    }

    this.getEndByteIndex = function() {
        return this.getStartByteIndex() + this.length();
    }

    this.getCssClass = function() {
        return "string-section";
    }

    this.getBytecode = function (index, constants) {
        var stringValue = getDirectString(stringindex, constants);
        return "String<span class='tab'>&nbsp;</span>" + stringindex +
        "<span class='tab'>&nbsp;</span>// " +
        stringValue;

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
