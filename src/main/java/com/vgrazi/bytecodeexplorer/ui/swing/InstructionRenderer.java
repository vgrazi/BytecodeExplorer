package com.vgrazi.bytecodeexplorer.ui.swing;

import com.vgrazi.bytecodeexplorer.structure.ClassFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Created by vgrazi on 11/16/15.
 */
public class InstructionRenderer extends MouseMotionAdapter {
    private ClassFile classFile;
    private JTable hexTable;
    private JLabel instructionPanel;

    public InstructionRenderer(ClassFile classFile, JTable hexTable, JLabel instructionPanel) {
        this.classFile = classFile;
        this.hexTable = hexTable;
        this.instructionPanel = instructionPanel;
    }

    private int lastRow = -1;
    private int lastCol = -1;
    @Override
    public void mouseMoved(MouseEvent e) {
        Point p = e.getPoint();
        int row = hexTable.rowAtPoint(p);
        int col = hexTable.columnAtPoint(p);

        synchronized (this) {
            if (row != lastRow || col != lastCol) {
                lastRow = row;
                lastCol = col;
                // todo: 16 should be replaced with TABLE_COLS
                int byteIndex = 16 * row + col;
                String instructionSequence = classFile.getInstructionSequence(byteIndex);
                instructionPanel.setText("<html>" + instructionSequence + "</html>");
            }
        }
    }

}
