//Code_attribute {
//    u2 attribute_name_index;
//    u4 attribute_length;
//    u2 max_stack;
//    u2 max_locals;
//    u4 code_length;
//    u1 code[code_length];
//    u2 exception_table_length;
//    {   u2 start_pc;
//        u2 end_pc;
//        u2 handler_pc;
//        u2 catch_type;
//    } exception_table[exception_table_length];
//    u2 attributes_count;
//    attribute_info attributes[attributes_count];
//}

function CodeAttribute(bytesArray, index) {
    this.bytes = bytesArray;
    var startByteIndex = index;
    var exceptions = [];
    var attributeNameIndex = getTwoBytes(bytesArray, index);
    var attributeLength = getFourBytes(bytesArray, index + 2);
    var maxStack = getTwoBytes(bytesArray, index + 6);
    var maxLocals = getTwoBytes(bytesArray, index + 8);
    var codeLength = getFourBytes(bytesArray, index + 10);
    var codeIndex = index + 14;
    var exceptionTableLengthIndex = codeIndex + codeLength
    var exceptionTableLength = getFourBytes(bytesArray, exceptionTableLengthIndex);
    var exceptionTableIndex = exceptionTableLengthIndex + 4

    var displacement = 0
    for(var i = 0; i < exceptionTableLength; i++) {
        var tableIndex = exceptionTableIndex + displacement;
        var exceptionTable = new ExceptionTable(bytesArray, tableIndex)
        exceptions.push(exceptionTable);
        displacement += exceptionTable.length;
    }

    var attributesCount = getTwoBytes(exceptionTableIndex + displacement);
    var arrayIndex = exceptionTableIndex + displacement + 2;
    var attributes = [];

    displacement = 0;
    for(var i = 0; i < attributesCount; i++) {
        var tableIndex = arrayIndex + displacement;
        var attributeInfo = new AttributeInfo(bytesArray, tableIndex);
        attributes.push(attributeInfo);
        displacement += attributeInfo.length;
    }

     this.getStartByteIndex = function() {
         return startByteIndex;
     }

     this.length = function() {
         return arrayIndex + displacement - startByteIndex;
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
        return "code-attribute-section";
    }

     this.getBytecode = function (index, constants) {
//     todo: encode attributes, etc
        var rval =
            formatAsFourByteHexString(attributeLength) + " Attribute length: " + attributeLength + "<br/>" +
            formatAsHexString(maxStack) + " Max stack: " + maxStack + "<br/>" +
            formatAsHexString(maxLocals) + " Max locals: " + maxLocals + "<br/>" +
            formatAsFourByteHexString(codeLength) + " Code length: " + codeLength + "<br/>" +
            decompile(codeIndex, bytesArray, codeLength);
        return rval;
     }
}