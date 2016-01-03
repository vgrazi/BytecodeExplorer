package com.vgrazi.bytecodeexplorer.structure;


import com.vgrazi.bytecodeexplorer.structure.members.MethodSection;

/**
 * Created by vgrazi on 8/13/15.
 */
public class ClassFile {
    private final byte[] bytes;
    private final MagicNumberSection magicNumberSection;
    private final BuildSection majorBuildSection;
    private final BuildSection minorBuildSection;
    private final CountSection constantPoolCountSection;
    private final ConstantPoolSection constantPoolSection;
    private final ClassAccessFlagsSection classAccessFlagsSection;
    private final ClassSection thisClassSection;
    private final ClassSection superClassSection;
    private final CountSection interfaceCountSection;
    private final ClassSection[] interfaceSections;
    private final CountSection fieldCountSection;
    private final FieldSection[] fieldSections;
    private final CountSection methodCountSection;
    private final MethodSection[] methodSections;

    public ClassFile(byte[] bytes) {
        this.bytes = bytes;
        magicNumberSection = new MagicNumberSection(bytes);
        minorBuildSection = new BuildSection("Minor", bytes, 4);
        majorBuildSection = new BuildSection("Major", bytes, 6);
        constantPoolCountSection = new CountSection("Pool count", bytes, 8);
        constantPoolSection = new ConstantPoolSection(constantPoolCountSection.getCount(), bytes, 10);
        classAccessFlagsSection = new ClassAccessFlagsSection(bytes, constantPoolSection.getEndByteIndex());
        thisClassSection = new ClassSection("This class", bytes, classAccessFlagsSection.getEndByteIndex());
        superClassSection = new ClassSection("Super class", bytes, thisClassSection.getEndByteIndex());
        interfaceCountSection = new CountSection("Interface count", bytes, superClassSection.getEndByteIndex());
        interfaceSections = new ClassSection[interfaceCountSection.getCount()];
        for (int i = 0; i < interfaceSections.length; i++) {
            interfaceSections[i] = new ClassSection("Interface", bytes, interfaceCountSection.getEndByteIndex() + 2 * i);
        }
        fieldCountSection = new CountSection("Field count", bytes, interfaceCountSection.getEndByteIndex() + interfaceCountSection.getCount() * 2);
        fieldSections = new FieldSection[fieldCountSection.getCount()];
        int nextByte = fieldCountSection.getStartByteIndex() + fieldCountSection.length();
        for (int i = 0; i < fieldSections.length; i++) {
            FieldSection fieldSection = new FieldSection(bytes, nextByte);
            fieldSections[i] = fieldSection;
            nextByte += fieldSection.length();
        }
        methodCountSection = new CountSection("Method count", bytes, nextByte);
        methodSections = new MethodSection[methodCountSection.getCount()];
        nextByte = methodCountSection.getStartByteIndex() + methodCountSection.length();
        for (int i = 0; i < methodSections.length; i++) {
            MethodSection methodSection = new MethodSection(bytes, nextByte);
            methodSections[i] = methodSection;
            nextByte += methodSection.length();
        }
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
        if (section != null) {
            return section.toString();
        }
        return "unknown";
    }

    /**
     * Used mostly for color coding the sections of the hex table, the section number starts at 0 and sequences
     * for each section, so magic number is section 0, minor build section 1, major=section 2, then the sections within the constant
     * pool continue from there in sequence.
     *
     * @param byteIndex The index in the classfile of the byte we are checking
     */
    public int getSectionIndex(int byteIndex) {
        if (magicNumberSection.contains(byteIndex)) {
            return 0;
        } else if (minorBuildSection.contains(byteIndex)) {
            return 1;
        } else if (majorBuildSection.contains(byteIndex)) {
            return 2;
        } else if (constantPoolCountSection.contains(byteIndex)) {
            return 3;
        } else if (constantPoolSection.contains(byteIndex)) {
            return constantPoolSection.getSectionIndex(byteIndex) + 4;
        } else if (classAccessFlagsSection.contains(byteIndex)) {
            return constantPoolCountSection.getCount() + 3;
        } else if (thisClassSection.contains(byteIndex)) {
            return constantPoolCountSection.getCount() + 4;
        } else if (superClassSection.contains(byteIndex)) {
            return constantPoolCountSection.getCount() + 5;
        } else if (interfaceCountSection.contains(byteIndex)) {
            return constantPoolCountSection.getCount() + 6;
        }

        for (int i = 0; i < interfaceSections.length; i++) {

            if (interfaceSections[i].contains(byteIndex)) {
                int val = constantPoolCountSection.getCount() + 7 + i;
                return val;
            }
        }

        if (fieldCountSection.contains(byteIndex)) {
            int val = constantPoolCountSection.getCount() + 7 + interfaceSections.length;
            return val;
        }

        for (int i = 0; i < fieldSections.length; i++) {
            if (fieldSections[i].contains(byteIndex)) {
                int val = constantPoolCountSection.getCount() + interfaceCountSection.getCount() + 8 + i;
                return val;
            }
        }

        if(methodCountSection.contains(byteIndex)) {
            return constantPoolCountSection.getCount() + interfaceCountSection.getCount() + fieldCountSection.getCount() + 9;
        }

        for (int i = 0; i < methodSections.length; i++) {
            if (methodSections[i].contains(byteIndex)) {
                int val = constantPoolCountSection.getCount() + interfaceCountSection.getCount() + fieldCountSection.getCount() + 10 + i;
                return val;
            }
        }

        return constantPoolCountSection.getCount() + interfaceCountSection.getCount() + fieldSections.length + fieldCountSection.getCount() + 10;
    }

    /**
     * Used mostly for color coding the sections of the hex table, the section number starts at 0 and sequences
     * for each section, so magic number is section 0, minor build section 1, major=section 2, then the sections within the constant
     * pool continue from there in sequence.
     *
     * @param byteIndex The index in the classfile of the byte we are checking
     */

    public ClassFileSection getSection(int byteIndex) {
        if (magicNumberSection.contains(byteIndex)) {
            return magicNumberSection;
        } else if (minorBuildSection.contains(byteIndex)) {
            return minorBuildSection;
        } else if (majorBuildSection.contains(byteIndex)) {
            return majorBuildSection;
        } else if (constantPoolCountSection.contains(byteIndex)) {
            return constantPoolCountSection;
        } else if (classAccessFlagsSection.contains(byteIndex)) {
            return classAccessFlagsSection;
        } else if (thisClassSection.contains(byteIndex)) {
            return thisClassSection;
        } else if (superClassSection.contains(byteIndex)) {
            return superClassSection;
        } else if (interfaceCountSection.contains(byteIndex)) {
            return interfaceCountSection;
        } else if (fieldCountSection.contains(byteIndex)) {
            return fieldCountSection;
        }
        for (ClassSection interfacesSection : interfaceSections) {
            if (interfacesSection.contains(byteIndex)) {
                return interfacesSection;
            }
        }

        for (FieldSection fieldSection : fieldSections) {
            if (fieldSection.contains(byteIndex)) {
                return fieldSection;
            }
        }

        if(methodCountSection.contains(byteIndex)) {
            return methodCountSection;
        }

        for(MethodSection methodSection : methodSections) {
            if(methodSection.contains(byteIndex)) {
                return methodSection;
            }
        }

        int sectionIndex = constantPoolSection.getSectionIndex(byteIndex);
        return constantPoolSection.getSection(sectionIndex);
    }

}
