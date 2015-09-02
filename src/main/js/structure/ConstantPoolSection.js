function ConstantPoolSection(elementCountVar, bytes, index) {

        var elementCount = elementCountVar;
//        var elementCount = 14;
        var startByteIndex = index;
        var length = 0;
        var constants = [];
        var selectedSection = null;
        var selectedIndex = null;

        // we will need the last one to determine the entire pool length
        var constantType = null;
        for(var i = 0; i < elementCount -1; i++) {
            var tag = bytes[index];
            constantType = createConstantType(tag, bytes, index);
            if(constantType != null) {
                constants.push(constantType);
                index += constantType.length();
            }
        }
        if (constantType != null) {
            length = constantType.getStartByteIndex() + constantType.length() - startByteIndex;
        }

    /**
     * How many elements in this section
     *
     * @return Number of elements in this section
     */
    this.elementCount = function() {
        return elementCount;
    }

    this.getSelectedIndex = function() {
        return selectedIndex;
    }

    /**
     * Index to the first byte of this section relative to the constant pool
     *
     * @return
     */
    this.getStartByteIndex = function() {
        return startByteIndex;
    }

    /**
     * length in bytes of this section
     *
     * @return length in bytes of this section
     */
    this.length = function() {
        return length;
    }

    this.getEndByteIndex = function () {
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

    this.getConstants = function() {
        return constants;
    }

    /**
     * Assuming the supplied index is the number of the constant (where the first is one) returns that constant
     */
    this.getConstant = function (baseOneIndex) {
        return constants[baseOneIndex -1];
    }


    this.getCssClass = function() {
        return "constant-pool-section";
    }

    /**
     * Index is the index into the byte array
     */
    this.getBytecode = function(index) {
        selectedSection = this.getSection(index);
        if(selectedSection != null) {
            return selectedIndex + " " + selectedSection.getBytecode(index, constants);
        }
    }

    this.getSection = function(index) {
        var section = null;
        constants.some(
            function(aSection, pointer) {

                if(aSection.contains(index)) {
                    section = aSection;
                    selectedIndex = pointer;
                    return true;
                }
                else {
                    return false;
                }
            }
        );
        return section;
    };
}
