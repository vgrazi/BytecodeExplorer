function AccessFlagsSection(bytesArray, startIndex) {

    this.bytes = bytesArray;
    var startByteIndex = startIndex;

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

    this.getCssClass = function() {
        return "access-flags-section";
    }

    this.getBytecode = function (index) {
        return "Class Access flags: " + accessFlags   + ": " + formatAsBinaryString(accessFlags) +
        " <br/>" +
            "<table>" +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x0001) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_PUBLIC</td><td class='access-table-mask'>0x0001</td><td>Declared public; may be accessed from outside its package.</td></tr>" +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x0010) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_FINAL</td><td class='access-table-mask'>0x0010</td><td>Declared final; no subclasses allowed.</td></tr>" +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x0020) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_SUPER</td><td class='access-table-mask'>0x0020</td><td>Treat superclass methods specially when invoked by the invokespecial instruction.</td></tr>" +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x0200) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_INTERFACE</td><td class='access-table-mask'>0x0200</td><td>Is an interface, not a class.</td></tr>" +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x0400) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_ABSTRACT</td><td class='access-table-mask'>0x0400</td><td>Declared abstract; must not be instantiated.</td></tr>" +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x1000) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_SYNTHETIC</td><td class='access-table-mask'>0x1000</td><td>Declared synthetic; not present in the source code.</td></tr>" +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x2000) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_ANNOTATION</td><td class='access-table-mask'>0x2000</td><td>Declared as an annotation type.</td></tr>" +
                "<tr><td class='access-table-mapped'></td><td class='access-table-desc " + ((accessFlags & 0x4000) != 0?"access-table-bold'":"access-table-dull'") + "'>ACC_ENUM</td><td class='access-table-mask'>0x4000</td><td>Declared as an enum type.</td></tr>" +
            "</table>";
    }

    var accessFlags = getTwoBytes(bytesArray, this.getStartByteIndex());

}
