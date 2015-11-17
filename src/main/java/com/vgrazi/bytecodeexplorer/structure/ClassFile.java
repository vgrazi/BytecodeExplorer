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

    public ClassFile(byte[] bytes) {
        this.bytes = bytes;
        magicNumberSection = new MagicNumberSection(bytes);
        minorBuildSection = new BuildSection("Minor", bytes, 4);
        majorBuildSection = new BuildSection("Major", bytes, 6);
        constantPoolCountSection = new CountSection(bytes);
        constantPoolSection = new ConstantPoolSection(constantPoolCountSection.getCountBytes(), bytes, 10);
        ConstantType.setConstants(constantPoolSection.getConstants());
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
        else {
            return constantPoolSection.getSectionIndex(byteIndex) + 3;
        }

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
        else {
            int sectionIndex = constantPoolSection.getSectionIndex(byteIndex);
            return constantPoolSection.getSection(sectionIndex);
        }

    }
}
