function ConstantNameAndType (elementCountVar, byteArray, index) {
    var bytes = byteArray;
    var startByteIndex = index;

    var nameIndex = getTwoBytes(bytes, index + 1);
    var descriptorIndex = getTwoBytes(bytes, index + 3);

    /**
     * "tag item" is how the documentation refers to the type byte
     *
     * @return
     */
    this.getTag = function() {
        return 12;
    }

    this.getReferenceIndex = function() {
        return nameIndex;
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
        return "name-and-type-section";
    }

    this.getBytecode = function () {
        return "NameAndTypef<span class='tab'>&nbsp;</span>#" + nameIndex + ".#" + descriptorIndex + "\t\t// ";// +
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
