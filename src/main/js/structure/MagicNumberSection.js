function MagicNumberSection(bytesArray) {

    this.getMagicNumber = function () {
        return magicNumber;
    }

    this.getStartByteIndex = function() {
        return 0;
    }

    this.length = function() {
        return 4;
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
        return "magic-number-section";
    }

    this.getBytecode = function (index) {
        return "Magic Number" + "<br/>"
        + formatAsOneByteHexString(0) + " " + formatAsOneByteHexString(bytesArray[0]) + "<br/>"
        + formatAsOneByteHexString(1) + " " + formatAsOneByteHexString(bytesArray[1]) + "<br/>"
        + formatAsOneByteHexString(2) + " " + formatAsOneByteHexString(bytesArray[2]) + "<br/>"
        + formatAsOneByteHexString(3) + " " + formatAsOneByteHexString(bytesArray[3]) + "<br/>" ;
    }

    var magicNumber = getFourBytes(bytesArray, this.getStartByteIndex());
}
