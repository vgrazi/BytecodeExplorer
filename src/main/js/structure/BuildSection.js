function BuildSection(bytesArray, index, name) {

    this.bytes = bytesArray;

    this.getStartByteIndex = function() {
        return index;
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


    this.getCssClass = function() {
        return "build-section";
    }

    this.getBytecode = function (index) {
        var byteString = getTwoBytes(bytesArray, this.getStartByteIndex());
        var formatted = formatAsHexString(byteString);
        return formatted + " " + name + " " + build;
    }

    var build = getTwoBytes(bytesArray, this.getStartByteIndex());

}
