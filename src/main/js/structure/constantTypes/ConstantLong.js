//CONSTANT_Long_info {
//    u1 tag;
//    u4 high_bytes;
//    u4 low_bytes;
//}
function ConstantLong (elementCountVar, byteArray, index) {
    var bytes = byteArray;
    var startByteIndex = index;
    var value = getNBytes(bytes, index + 1, 8);


    /**
     * "tag item" is how the documentation refers to the type byte
     *
     * @return
     */
    this.getTag = function() {
        return 5;
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
        return 9;
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
