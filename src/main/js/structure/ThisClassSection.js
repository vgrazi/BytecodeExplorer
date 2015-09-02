function ThisClassSection (byteArray, index) {
    var bytes = byteArray;
    var startByteIndex = index;
    var classIndex = getTwoBytes(bytes, index);

    /**
     * Index to the first byte of this section relative to the constant pool
     *
     * @return
     */
    this.getStartByteIndex = function() {
        return startByteIndex;
    }

    this.getReferenceIndex = function() {
        return classIndex;
    }

    /**
     * length in bytes of this section
     *
     * @return length in bytes of this section
     */
    this.length = function() {
        return 2;
    }

    this.getEndByteIndex = function() {
        return this.getStartByteIndex() + this.length();
    }

    this.getCssClass = function() {
        return "this-class-section";
    }



    this.getBytecode = function (index, constants) {
        var classStringValue = getIndirectString(classIndex, constants);
        return "This class<span class='tab'>&nbsp;</span>#" + classIndex +
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
