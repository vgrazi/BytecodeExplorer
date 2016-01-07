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
    private static int mouseByteIndex;

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
        int byteIndex = getByteIndex(point);
        ClassFileSection section = classFile.getSection(byteIndex);
        int clickedStartIndex = section.getStartByteIndex();

        if(this.clickedStartIndex == clickedStartIndex) {
            System.out.printf("resetting to null start:%d end:%d%n", getStartHighlightBlock(), getEndHighlightBlock());
            setClickedStartIndex(-1);
        }
        else {
            setClickedStartIndex(clickedStartIndex);
            System.out.printf("setting point to " + point);
            renderPoint(e);
        }
    }

    /**
     * Returns the byte index into the hex table associated with the screen coordinates
     * @param point
     * @return
     */
    private int getByteIndex(Point point) {
        int row = hexTable.rowAtPoint(point);
        int column = hexTable.columnAtPoint(point);
        return row * TABLE_COLUMNS + column;
    }


    private void renderPoint(MouseEvent e) {
        int byteIndex;
        if (isInside()) {
            // work with Point in case mouseByteIndex is reassigned in a contending thread
            Point point = e.getPoint();
            mouseByteIndex = getByteIndex(point);
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
        }
        hexTable.repaint();
    }

    public static int getStartHighlightBlock() {
        return startHighlightBlock;
    }

    public static int getEndHighlightBlock() {
        return endHighlightBlock;
    }

    public static int getMouseByteIndex() {
        return mouseByteIndex;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setInside(true);
        renderPoint(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setInside(false);

        // reset the mouse byte on exit so it does not stay rendered
        mouseByteIndex = -1;
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

