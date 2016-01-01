package com.vgrazi.bytecodeexplorer.ui.swing;

import com.vgrazi.bytecodeexplorer.structure.ClassFile;
import com.vgrazi.bytecodeexplorer.structure.ClassFileSection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PointSelector implements MouseListener, MouseMotionListener{


    public static final int TABLE_COLUMNS = 16;
    private final InstructionRenderer instructionRenderer;
    private JTable hexTable;
    private int clickedStartIndex;
    private boolean inside;
    private static int startHighlightBlock = -1;
    private static int endHighlightBlock = -1;
    private ClassFile classFile;

    public PointSelector(InstructionRenderer instructionRenderer, JTable hexTable, ClassFile classFile) {

        this.instructionRenderer = instructionRenderer;
        this.hexTable = hexTable;
        this.classFile = classFile;
    }

    public void mouseMoved(MouseEvent e) {
        renderPoint(e);
        setInside(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = e.getPoint();
        int row = hexTable.rowAtPoint(point);
        int column = hexTable.columnAtPoint(point);
        int byteIndex = row * TABLE_COLUMNS + column;
        ClassFileSection section = classFile.getSection(byteIndex);
        int startHighlightBlock = section.getStartByteIndex();
/*
  // the problem here is that mouseEntered, mouseExited, and mouseMoved all call render, which sets this.startHighlightBlock
  // before mouseClicked ever gets to it

        if(getStartHighlightBlock() == startHighlightBlock) {
            System.out.printf("resetting to null start:%d end:%d%n", getStartHighlightBlock(), getEndHighlightBlock());
            setClickedStartIndex(-1);
        }
        else
//*/
        {
            setClickedStartIndex(startHighlightBlock);
            System.out.printf("setting point to " + point);
            renderPoint(e);
        }
    }


    private void renderPoint(MouseEvent e) {
        Point point;
        int byteIndex;
        if (isInside()) {
            point = e.getPoint();
            int row = hexTable.rowAtPoint(point);
            int column = hexTable.columnAtPoint(point);
            byteIndex = row * TABLE_COLUMNS + column;
        }
        else {
            byteIndex = getClickedStartIndex();
        }

        if (byteIndex != -1) {

            selectBlock(byteIndex);
            instructionRenderer.renderInstructions(byteIndex);
        }
    }

    private void selectBlock(int byteIndex) {
        if(byteIndex < startHighlightBlock || byteIndex > endHighlightBlock) {
            ClassFileSection section = classFile.getSection(byteIndex);
            startHighlightBlock = section.getStartByteIndex();
            endHighlightBlock = startHighlightBlock + section.length() - 1;
            hexTable.repaint();
        }

    }

    public static int getStartHighlightBlock() {
        return startHighlightBlock;
    }

    public static int getEndHighlightBlock() {
        return endHighlightBlock;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setInside(true);
        renderPoint(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setInside(false);
        renderPoint(e);
    }

    public int getClickedStartIndex() {
        return clickedStartIndex;
    }

    public void setClickedStartIndex(int clickedStartIndex) {
        System.out.println("Setting point to " + clickedStartIndex);
        this.clickedStartIndex = clickedStartIndex;
    }

    public boolean isInside() {
        return inside;
    }

    public void setInside(boolean inside) {
        this.inside = inside;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

}

