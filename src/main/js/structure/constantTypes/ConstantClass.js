function ConstantClass (elementCountVar, bytes, index) {

    var startByteIndex = index;
    var nameindex = getTwoBytes(bytes, index + 1);

    var length;

    var elementCount = elementCountVar;
    var constants = [];

    this.getTag = function() {
        return 7;
    }

    this.getReferenceIndex = function() {
        return nameindex;
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
        return "class-section";
    }

    this.getBytecode = function(index, constants) {
        var classStringValue = getDirectString(nameindex, constants);
        return "Class<span class='tab'>&nbsp;</span> #" + nameindex +
            "<span class='tab'>&nbsp;</span>// " +
            classStringValue;
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
