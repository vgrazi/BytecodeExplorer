//field_info
//    u2             access_flags;
//    u2             name_index;
//    u2             descriptor_index;
//    u2             attributes_count;
//    attribute_info attributes[attributes_count];


function FieldsSection(bytesArray, index, fieldsCount) {

    this.bytes = bytesArray;
    var startByteIndex = index;
    var fields = [];
    var selectedSection = null;
    var length = 0;

    var fieldIndex = index;
    var lastFieldInfo = null;
    for(var i = 0; i < fieldsCount; i++) {
        var fieldInfo = new FieldInfo(bytesArray, fieldIndex);
        fields.push(fieldInfo);
        fieldIndex += fieldInfo.length();
        lastFieldInfo = fieldInfo;
    }


    if(lastFieldInfo != null) {
        length = lastFieldInfo.getStartByteIndex() + lastFieldInfo.length() - index;
    }

    this.getStartByteIndex = function() {
        return startByteIndex;
    }

    this.length = function() {
        return length;
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
        return "fields-section";
    }

    this.getBytecode = function (index, constants) {
        var rval = "Not mapped";
        if(selectedSection != null) {
            // index is not currently used
            return selectedSection.getBytecode(index, constants);
        }
    }


    this.getFieldAtByteIndex = function(index) {
        for(var i = 0; i < fieldsCount; i++) {
            var section = fields[i];
            if(section.contains(index)) {
                selectedSection = section;
                return section;
            }
        }
        return null;
    }

    this.getSelectedSection = function() {
        return selectedSection;
    }
}
