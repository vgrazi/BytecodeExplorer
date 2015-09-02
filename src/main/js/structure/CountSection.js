function CountSection(bytesArray, index, name) {

    this.bytes = bytesArray;
    var startByteIndex = index;

    this.getStartByteIndex = function() {
        return startByteIndex;
    }

    this.length = function() {
        return 2;
    }

    this.getEndByteIndex = function() {
        return this.getStartByteIndex() + this.length();
    }

    this.contains = function (index) {
        if(index >= this.getStartByteIndex() && index < this.getStartByteIndex() + this.length()) {
            return true;
        }
        else {
            return false;
        }
    }

    this.getCount = function() {
        return count;
    }


    this.getCssClass = function() {
        return "count-section";
    }

    this.getBytecode = function (index) {
        return formatAsHexString(count) + " " + name + ": " + count;   +
        " <br/>";
    }

    var count = getTwoBytes(this.bytes, this.getStartByteIndex())


}
