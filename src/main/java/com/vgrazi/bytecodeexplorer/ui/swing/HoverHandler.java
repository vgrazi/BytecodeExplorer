package com.vgrazi.bytecodeexplorer.ui.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Created by vgrazi on 11/19/15.
 */
public class HoverHandler extends MouseMotionAdapter {
    private static final int TABLE_COLUMNS = 16;
    private final JTable hexTable;
    private BytecodeRenderer cellRenderer;

    public HoverHandler(JTable hexTable, BytecodeRenderer cellRenderer) {

        this.hexTable = hexTable;
        this.cellRenderer = cellRenderer;
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        Point p = e.getPoint();
        int row = hexTable.rowAtPoint(p);
        int column = hexTable.columnAtPoint(p);
        int byteIndex = row * TABLE_COLUMNS + column;
        cellRenderer.hover(byteIndex);
    }
}
