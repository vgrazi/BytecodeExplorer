//field_info {
//    u2             access_flags;
//    u2             name_index;
//    u2             descriptor_index;
//    u2             attributes_count;
//    attribute_info attributes[attributes_count];
//}

function FieldInfo(bytesArray, index) {
    this.bytes = bytesArray;
    var startByteIndex = index;
    var accessFlags = getTwoBytes(bytesArray, index);
    var nameIndex = getTwoBytes(bytesArray, index + 2);
    var descriptorIndex = getTwoBytes(bytesArray, index + 4);
    var attributesCount = getTwoBytes(bytesArray, index + 6);
    var attributes = [];
    var attributesLength = 0;
    var arrayIndex = index + 8;
    for(var i = 0; i < attributesCount; i++) {
        var attributeInfo = new AttributeInfo(bytesArray, arrayIndex);
        attributes.push(attributeInfo);
        attributesLength += attributeInfo.length();
        arrayIndex += attributeInfo.length();
    }

     this.getStartByteIndex = function() {
         return startByteIndex;
     }

     this. length = function() {
         return attributesLength + 8;
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
         return "field-info-section";
     }

     this.getBytecode = function (index, constants) {
        var descriptor = getDirectString(descriptorIndex, constants);
        var lookup = null;
        if(descriptor.indexOf("L") == 0) {
            lookup = "";
        }
        else {
            lookup = descriptorMap[descriptor];
        }
        var rval = "Field: <br/>" +
        formatAsHexString(accessFlags) + " Access flags:" + formatAsBinaryString(accessFlags) + "</br>" +
        formatAsHexString(nameIndex) + " #" + nameIndex + " " + getDirectString(nameIndex, constants) + "<br/>" +
        formatAsHexString(descriptorIndex) + " #" + descriptorIndex + " " + descriptor + " " + lookup + "<br/>" +
        formatAsHexString(attributesCount) + " Attributes count: " + attributesCount + "<br/>";
        for(var i = 0; i < attributes.length; i++) {
            var attribute = attributes[i];
            rval += attribute.getBytecode(index, constants) + "<br/>";
        }
        rval +=
        " <br/>" +
            "<table>" +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x0001) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_PUBLIC	    </td><td class='access-table-mask'>0x0001</td><td>Declared public; may be accessed from outside its package." +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x0002) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_PRIVATE	    </td><td class='access-table-mask'>0x0002</td><td>Declared private; usable only within the defining class." +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x0004) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_PROTECTED	</td><td class='access-table-mask'>0x0004</td><td>Declared protected; may be accessed within subclasses." +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x0008) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_STATIC	    </td><td class='access-table-mask'>0x0008</td><td>Declared static." +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x0010) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_FINAL   	</td><td class='access-table-mask'>0x0010</td><td>Declared final; never directly assigned to after object construction (JLS §17.5)." +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x0040) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_VOLATILE	</td><td class='access-table-mask'>0x0040</td><td>Declared volatile; cannot be cached." +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x0080) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_TRANSIENT	</td><td class='access-table-mask'>0x0080</td><td>Declared transient; not written or read by a persistent object manager." +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x1000) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_SYNTHETIC	</td><td class='access-table-mask'>0x1000</td><td>Declared synthetic; not present in the source code." +
            "</table><br/>";

        return rval;
     }
}