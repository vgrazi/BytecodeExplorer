package com.vgrazi.bytecodeexplorer.structure;


import com.vgrazi.bytecodeexplorer.structure.constantTypes.ConstantType;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ClassFile {
    private final byte[] bytes;
    private MagicNumberSection magicNumberSection;
    private BuildSection majorBuildSection;
    private BuildSection minorBuildSection;
    private CountSection constantPoolCountSection;
    private ConstantPoolSection constantPoolSection;
    private AccessFlagsSection accessFlagsSection;
    private ClassSection thisClassSection;
    private ClassSection superClassSection;

    public ClassFile(byte[] bytes) {
        this.bytes = bytes;
        magicNumberSection = new MagicNumberSection(bytes);
        minorBuildSection = new BuildSection("Minor", bytes, 4);
        majorBuildSection = new BuildSection("Major", bytes, 6);
        constantPoolCountSection = new CountSection(bytes);
        constantPoolSection = new ConstantPoolSection(constantPoolCountSection.getCountBytes(), bytes, 10);
        ConstantType.setConstants(constantPoolSection.getConstants());
        accessFlagsSection = new AccessFlagsSection(bytes, constantPoolSection.getEndByteIndex());
        thisClassSection = new ClassSection("This class", bytes, accessFlagsSection.getEndByteIndex(), constantPoolSection.getConstants());
        superClassSection = new ClassSection("Super class", bytes, thisClassSection.getEndByteIndex(), constantPoolSection.getConstants());
//        var interfacesCountSection = new CountSection(bytes, superClassSection.getEndByteIndex(), "Interfaces count");
//        var interfacesSection = new InterfacesSection(bytes, interfacesCountSection.getEndByteIndex(), interfacesCountSection.getCount());
//        var fieldsCountSection = new CountSection(bytes, interfacesSection.getEndByteIndex(), "Fields count");
//        var fieldsSection = new FieldsSection(bytes, fieldsCountSection.getEndByteIndex(), fieldsCountSection.getCount());
//        var methodCountSection = new CountSection(bytes, fieldsSection.getEndByteIndex(), "Methods count");
//        var methodsSection = new MethodsSection(bytes, methodCountSection.getEndByteIndex(), methodCountSection.getCount());

    }

    public byte[] getBytes() {
        return bytes;
    }

    public int length() {
        return bytes.length;
    }

    public byte getByte(int index) {
        return bytes[index];
    }

    public String getInstructionSequence(int hoverByteIndex) {
        // first determine the section, then handle appropriately
        ClassFileSection section = getSection(hoverByteIndex);
        if(section != null) {
            return section.toString();
        }
        return "unknown";
    }

    /**
     * Used mostly for color coding the sections of the hex table, the section number starts at 0 and sequences
     * for each section, so magic number is section 0, minor build section 1, major=section 2, then the sections within the constant
     * pool continue from there in sequence.
     * @param byteIndex The index in the classfile of the byte we are checking
     */
    public int getSectionIndex(int byteIndex) {
        if(magicNumberSection.contains(byteIndex)) {
            return 0;
        }
        else if(minorBuildSection.contains(byteIndex)) {
            return 1;
        }
        else if(majorBuildSection.contains(byteIndex)) {
            return 2;
        }
        else if(constantPoolCountSection.contains(byteIndex)) {
            return 3;
        }
        else if(constantPoolSection.contains(byteIndex)){
            return constantPoolSection.getSectionIndex(byteIndex) + 4;
        }
        else if(accessFlagsSection.contains(byteIndex)) {
            return constantPoolCountSection.getCountBytes() + 4;
        }
        else if(thisClassSection.contains(byteIndex)) {
            return constantPoolCountSection.getCountBytes() + 6;
        }
        else if(superClassSection.contains(byteIndex)) {
            return constantPoolCountSection.getCountBytes() + 8;
        }
        else
        return constantPoolCountSection.getCountBytes() + 10;
    }

    /**
     * Used mostly for color coding the sections of the hex table, the section number starts at 0 and sequences
     * for each section, so magic number is section 0, minor build section 1, major=section 2, then the sections within the constant
     * pool continue from there in sequence.
     * @param byteIndex The index in the classfile of the byte we are checking
     */
    public ClassFileSection getSection(int byteIndex) {
        if(magicNumberSection.contains(byteIndex)) {
            return magicNumberSection;
        }
        else if(minorBuildSection.contains(byteIndex)) {
            return minorBuildSection;
        }
        else if(majorBuildSection.contains(byteIndex)) {
            return majorBuildSection;
        }
        else if(constantPoolCountSection.contains(byteIndex)) {
            return constantPoolCountSection;
        }
        else if(accessFlagsSection.contains(byteIndex)) {
            return accessFlagsSection;
        }
        else if(thisClassSection.contains(byteIndex)) {
            return thisClassSection;
        }
        else if(superClassSection.contains(byteIndex)) {
            return superClassSection;
        }

        else {
            int sectionIndex = constantPoolSection.getSectionIndex(byteIndex);
            return constantPoolSection.getSection(sectionIndex);
        }

    }
}
