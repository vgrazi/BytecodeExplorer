package com.vgrazi.bytecodeexplorer.ui.swing;

import com.vgrazi.bytecodeexplorer.structure.ClassFile;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by vgrazi on 11/14/15.
 */
public class BytecodeExplorerCellRenderer extends DefaultTableCellRenderer{
    private ClassFile classFile;

    public BytecodeExplorerCellRenderer(ClassFile classFile) {

        this.classFile = classFile;
    }

//    private static Color[] backgroundColors = new Color[]{Color.pink, Color.yellow, Color.ORANGE, Color.LIGHT_GRAY, Color.white};
    private static Color[] backgroundColors = new Color[]{Color.LIGHT_GRAY, Color.white};
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        // todo: don't hardcode 16, refer to the TABLE_COLUMNS
        int byteIndex = row * 16 + column;
        int sectionIndex = classFile.getSectionIndex(byteIndex);
        Color color = backgroundColors[sectionIndex%backgroundColors.length];

        component.setBackground(color);
        return component;
    }
}
