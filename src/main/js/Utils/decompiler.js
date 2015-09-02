function decompile(startIndex, bytes, byteCount) {
    var instructions = [];
    for(var i = 0; i < byteCount; ) {
        var index = startIndex + i;
        var byte = bytes[index];
        var instruction = null;
        var increment;
        // careful, this is = not ==, we are assigning and checking for undefined
        if(instruction = map[byte]) {
            instructions.push(getCodeString(i, byte, instruction));
            increment = 1;
        }
        else if(instruction = map1[byte]) {
            instructions.push(getCodeString(i, byte, instruction, bytes, index + 1, 1));
            increment = 2;
        }
        else if(instruction = map2[byte]) {
            instructions.push(getCodeString(i, byte, instruction, bytes, index + 1, 2));
            increment = 3;
        }
        else if(instruction = map3[byte]) {
            instructions.push(getCodeString(i, byte, instruction, index + 1, 3));
            increment = 4;
        }
        else if(instruction = map4[byte]) {
            instructions.push(getCodeString(i, byte, instruction, bytes, index + 1, 4));
            increment = 5;
        }
        else {
            throw new Error("Unknown bytecode instruction code " + formatAsHexString(byte));
        }
        i+= increment;
    }

    return "<table class='table table-striped table-condensed'>" + instructions.join("&nbsp;") + "</table>";
}

function getCodeString(lineNumber, byte, instruction, bytes, index, bytecount) {
    return "<tr><td class='decompile-row'>" + lineNumber + ":</td><td  class='decompile-byte'>" + formatAsOneByteHexString(byte) + "</td><td class='decompile-instruction'>" + instruction  + "</td><td  class='decompile-args'>" +
        formatAsHexStringFromArray(bytes, index, bytecount) + "</td></tr>";
}