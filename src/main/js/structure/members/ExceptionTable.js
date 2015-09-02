//exception_table;
//    {   u2 start_pc;
//        u2 end_pc;
//        u2 handler_pc;
//        u2 catch_type;
//    } exception_table[exception_table_length];
function ExceptionTable(bytes, index) {
    var startPc = getTwoBytes(bytes, index);
    var endPc = getTwoBytes(bytes, index+2);
    var handlerPc = getTwoBytes(bytes, index+4);
    var catchType = getTwoBytes(bytes, index+6);

    this.getBytecode = function (index, constants) {
        var rval =
         formatAsHexString(startPc) + "<br/>" +
         formatAsHexString(endPc) + "<br/>" +
         formatAsHexString(handlerPc) + "<br/>" +
         formatAsHexString(catchType) + "<br/>";
    }

     this.getStartByteIndex = function() {
         return index;
     }

    this.length = function() {
        return 8;
    }
}