//attribute_info {
//    u2 attribute_name_index;
//    u4 attribute_length;
//    u1 info[attribute_length];
//}

function AttributeInfo(bytesArray, index) {
    this.bytes = bytesArray;
    var startByteIndex = index;
    var nameIndex = getTwoBytes(bytesArray, index);
    var attributeLength = getFourBytes(bytesArray, index + 2);
    var info = null; // todo:Add this

    this.length = function() {
        return 6 + attributeLength;
    }

    this.getCssClass = function() {
        return "attribute-info-section";
    }

     this.getBytecode = function (indexUnused, constants) {
        var attributeName = getDirectString(nameIndex, constants);
        var code = "";
        if(attributeName == "Code") {
            var codeAttribute = new CodeAttribute(bytesArray, index)
            code = codeAttribute.getBytecode(index, constants);
        }
        return formatAsHexString(nameIndex) + " @" + attributeName + "<br/>" + code;
     }

     this.getReferenceIndex = function() {
         return nameIndex;
     }

}
