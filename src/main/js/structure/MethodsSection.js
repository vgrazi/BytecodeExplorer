//method_info
//    u2             access_flags;
//    u2             name_index;
//    u2             descriptor_index;
//    u2             attributes_count;
//    attribute_info attributes[attributes_count];


function MethodsSection(bytesArray, index, methodsCount) {

    this.bytes = bytesArray;
    var startByteIndex = index;
    var methods = [];
    var selectedSection = null;
    var length = 0;

    var methodIndex = index;
    var lastMethodInfo = null;
    for(var i = 0; i < methodsCount; i++) {
        var methodInfo = new MethodInfo(bytesArray, methodIndex);
        methods.push(methodInfo);
        methodIndex += methodInfo.length();
        lastMethodInfo = methodInfo;
    }

    if(lastMethodInfo != null) {
        length = lastMethodInfo.getStartByteIndex() + lastMethodInfo.length() - index;
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
        return "methods-section";
    }



    this.getBytecode = function (index, constants) {
        var rval = "Not mapped";
        if(selectedSection != null) {
            // index is not currently used
            return selectedSection.getBytecode(index, constants);
        }
    }


    this.getMethodAtByteIndex = function(index) {
        for(var i = 0; i < methodsCount; i++) {
            var section = methods[i];
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
