package com.vgrazi.bytecodeexplorer.ui.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by vgrazi on 11/14/15.
 */
public class BytecodeExplorerCellRenderer extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(row == 2 && column == 2) {
            component.setBackground(Color.green);
        }
        else {
            component.setBackground(Color.yellow);
        }
        return component;
    }
}
