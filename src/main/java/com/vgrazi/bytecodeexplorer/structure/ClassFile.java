package com.vgrazi.bytecodeexplorer.structure;


/**
 * Created by vgrazi on 8/13/15.
 */
public class ClassFile {
    private final byte[] bytes;
    private MagicNumberSection magicNumberSection;
    private BuildSection majorBuildSection;
    private BuildSection minorBuildSection;
    private ConstantPoolCountSection constantPoolCountSection;
    private ConstantPoolSection constantPoolSection;

    public ClassFile(byte[] bytes) {
        this.bytes = bytes;
        magicNumberSection = new MagicNumberSection(bytes);
        minorBuildSection = new BuildSection(bytes, 4);
        majorBuildSection = new BuildSection(bytes, 6);
        constantPoolCountSection = new ConstantPoolCountSection(bytes);
        constantPoolSection = new ConstantPoolSection(constantPoolCountSection.getPoolSizeBytes(), bytes, 10);
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
        return "";
    }
}
