

    function pack(bytes) {
        var chars = [];
        for(var i = 0, n = bytes.length; i < n;) {
            chars.push(((bytes[i++] & 0xff) << 8) | (bytes[i++] & 0xff));
        }
        return String.fromCharCode.apply(null, chars);
    }

    function unpack(str) {
        var bytes = [];
        for(var i = 0, n = str.length; i < n; i++) {
            var char = str.charCodeAt(i);
            bytes.push(char & 0xFF);
        }
        return bytes;
    }

    function getByte(bytes, index) {
        var value = bytes[index];
        return value;
    }

    function getTwoBytes(bytes, index) {
        var value =
            bytes[index] * 256 + bytes[index + 1];
        return value;
    }

    function getFourBytes(bytes, index) {
        var value = getTwoBytes(bytes, index) * 256 * 256 + getTwoBytes(bytes, index + 2);

        return value;
    }

    function getNBytes(bytes, index, n) {
        var value = 0;
        for(var i = 0; i < n; i++) {
            value = value * 256 + bytes[index + i];
        }
        return value;
    }

    function getIndirectString(index, constants) {
        // index points to a constant. Extract that, then find where it points
        var constant = constants[index -1];
        var utfIndex = constant.getReferenceIndex();
        return getDirectString(utfIndex, constants);
    }

    function getDirectString(index, constants) {
        var utfConstant = constants[index -1];
        var stringValue = utfConstant.getStringValue();
        return stringValue;
    }

    function getText(bytes, index) {
        var type = getTwoBytes(bytes, index);
        var length = getTwoBytes(bytes, index + 2);
        var array = bytes.slice(index + 4, index + 4 + length);
        return bin2String(array);

    }

    function bin2String(array) {
      var result = "";
      for (var i = 0; i < array.length; i++) {
        result += String.fromCharCode(array[i]);
      }
      return result;
    }

    function formatAsBinaryString(value) {
        var whole = "0000000000000000" + value.toString(2);
         var formatted = whole.substring(whole.length - 16)
         return formatted.substring(0, 4) + " " +
         formatted.substring(4, 8) + " " +
         formatted.substring(8, 12) + " " +
         formatted.substring(12, 16);
    }

    function formatAsHexString(value) {
//        return convertToHex(value);
         var whole = "0000" + value.toString(16);
         var formatted = whole.substring(whole.length - 4).toUpperCase();
         return formatted.substring(0, 2) + " " + formatted.substring(2, 4);
    }

    function formatAsHexStringFromArray(bytesArray, index, length) {
        var rval = [];
        for(var i = 0; i < length; i++) {
            var byte = bytesArray[index + i];
            rval.push(formatAsOneByteHexString(byte))
        }
        return rval.join();
    }

    function formatAsOneByteHexString(byte) {
         var whole = "00" + byte.toString(16);
         var formatted = whole.substring(whole.length - 2).toUpperCase();
         return formatted;
    }

    function formatAsFourByteHexString(value) {
//        return convertToHex(value);
        var whole = "00000000" + value.toString(16);
         var formatted = whole.substring(whole.length - 8).toUpperCase();
         return formatted.substring(0, 2) + " " + formatted.substring(2, 4) + " " + formatted.substring(4, 6) + " " + formatted.substring(6, 8);
    }

    function convertToHex(intValue) {
        var h = ("0" + intValue.toString(16)).substr(-2).toUpperCase();
        return h;
    }

