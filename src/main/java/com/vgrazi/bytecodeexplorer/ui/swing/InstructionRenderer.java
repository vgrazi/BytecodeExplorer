package com.vgrazi.bytecodeexplorer.ui.swing;

import com.vgrazi.bytecodeexplorer.structure.ClassFile;

import javax.swing.*;

public class InstructionRenderer {
    private ClassFile classFile;
    private JLabel instructionPanel;

    public InstructionRenderer(ClassFile classFile, JLabel instructionPanel) {
        this.classFile = classFile;
        this.instructionPanel = instructionPanel;
    }

    public void renderInstructions(int byteIndex) {
        String instructionSequence = classFile.getInstructionSequence(byteIndex);
        instructionPanel.setText("<html>" + instructionSequence + "</html>");
    }

}
