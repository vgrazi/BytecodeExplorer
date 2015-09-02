function InterfacesSection(bytesArray, index, interfaceCount) {

    this.bytes = bytesArray;
    var startByteIndex = index;
    var interfacePointers = [];

    this.getStartByteIndex = function() {
        return startByteIndex;
    }

    this.length = function() {
        return interfaceCount * 2;
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
        return "interfaces-section";
    }

    this.getBytecode = function (index, constants) {
        var interfaces = "";
        for(var i = 0; i < interfacePointers.length; i++) {
            interfaces += "<br/>" +
            formatAsHexString(interfacePointers[i]) +
            " #" + interfacePointers[i] +
            " " + getIndirectString(interfacePointers[i], constants);
        }
        return "Interfaces: " +  interfaces;
    }

    for(var i = 0; i < interfaceCount; i++) {
        var index = startByteIndex + i * 2;
        var pointer = getTwoBytes(this.bytes, index);
        interfacePointers.push(pointer);
    }
}
