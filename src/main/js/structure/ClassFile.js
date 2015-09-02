function ClassFile(bytesArray) {

    var  bytes = bytesArray;

    var magicNumberSection = new MagicNumberSection(bytes);
    var minorBuildSection = new BuildSection(bytes, 4, "Minor build:");
    var majorBuildSection = new BuildSection(bytes, 6, "Major build:");
    var constantPoolCountSection = new CountSection(bytes, majorBuildSection.getEndByteIndex(), "Pool count");
    var constantPoolSection = new ConstantPoolSection(constantPoolCountSection.getCount(), bytes, 10);
    var accessFlagsSection = new AccessFlagsSection(bytes, constantPoolSection.getEndByteIndex());
    var thisClassSection = new ThisClassSection(bytes, accessFlagsSection.getEndByteIndex());
    var superClassSection = new SuperClassSection(bytes, thisClassSection.getEndByteIndex());
    var interfacesCountSection = new CountSection(bytes, superClassSection.getEndByteIndex(), "Interfaces count");
    var interfacesSection = new InterfacesSection(bytes, interfacesCountSection.getEndByteIndex(), interfacesCountSection.getCount());
    var fieldsCountSection = new CountSection(bytes, interfacesSection.getEndByteIndex(), "Fields count");
    var fieldsSection = new FieldsSection(bytes, fieldsCountSection.getEndByteIndex(), fieldsCountSection.getCount());
    var methodCountSection = new CountSection(bytes, fieldsSection.getEndByteIndex(), "Methods count");
    var methodsSection = new MethodsSection(bytes, methodCountSection.getEndByteIndex(), methodCountSection.getCount());

    this.length = function () {
        return bytes.length;
    }

    this.getByte = function(index) {
        return bytes[index];
    }

    this.getBytecode = function(index) {
        var section = this.getSection(index);
        if(section != null) {
            var bytecode = this.selectedIndex + section.getBytecode(index, constantPoolSection.getConstants());

            return bytecode;
        }
        else {
            return "Unmapped";
        }
    }

    this.getSelectedSection = function() {
        return this.selectedSection;
    }

    this.getSection = function(index) {
        this.selectedIndex = "";

        if(magicNumberSection.contains(index)) {
            this.selectedSection = magicNumberSection;
        }
        else if(minorBuildSection.contains(index)) {
            this.selectedSection = minorBuildSection;
        }
        else if(majorBuildSection.contains(index)) {
            this.selectedSection = majorBuildSection;
        }
        else if(constantPoolCountSection.contains(index)) {
            this.selectedSection = constantPoolCountSection;
        }
        else if(constantPoolSection.contains(index)) {
            this.selectedSection = constantPoolSection.getSection(index);

            var byteString = this.selectedSection.getTag();
            var formatted = formatAsOneByteHexString(byteString);

            this.selectedIndex = "(#" + (constantPoolSection.getSelectedIndex() + 1) + ") " + formatted + " ";
        }
        else if(accessFlagsSection.contains(index)) {
            this.selectedSection = accessFlagsSection;
        }
        else if(thisClassSection.contains(index)) {
            this.selectedSection = thisClassSection;
        }
        else if(superClassSection.contains(index)) {
            this.selectedSection = superClassSection;
        }
        else if(interfacesCountSection.contains(index)) {
            this.selectedSection = interfacesCountSection;
        }
        else if(interfacesSection.contains(index)) {
            this.selectedSection = interfacesSection;
        }
        else if(fieldsCountSection.contains(index)) {
            this.selectedSection = fieldsCountSection;
        }
        else if(fieldsSection.contains(index)) {
            this.selectedSection = fieldsSection.getFieldAtByteIndex(index);
        }
        else if(methodCountSection.contains(index)) {
            this.selectedSection = methodCountSection;
        }
        else if(methodsSection.contains(index)) {
            this.selectedSection = methodsSection.getMethodAtByteIndex(index);
        }
        else {
            this.selectedSection = null;
        }

        return this.selectedSection;
    }


    this.isConstantPoolIndex = function (index) {
        var rval = false;
        if(constantPoolSection) {
            rval = constantPoolSection.contains(index);
        }
        return rval;
    }

    this.getEndByteIndex = function (byteIndexStart) {
        if(this.selectedSection) {
            return this.selectedSection.getEndByteIndex(byteIndexStart);
        }

    }

    this.getInstructionSequence = function (hoverByteIndex) {
        // first determine the section, then handle appropriately
        return "";
    }

    this.getCssClass = function(section) {
        return section.getCssClass();
    }

    this.getSelectedRange = function() {
        var section = this.getSelectedSection();
        if(section != null) {
            return section.getStartByte() + "-" + section.length();
        }
        else {
            return "none";
        }
    }

    this.getConstants = function() {
        return constantPoolSection.getConstants();
    }
}
