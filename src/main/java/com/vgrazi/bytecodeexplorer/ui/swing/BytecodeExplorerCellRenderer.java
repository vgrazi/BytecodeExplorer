package com.vgrazi.bytecodeexplorer.ui.swing;

import com.vgrazi.bytecodeexplorer.structure.ClassFile;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class BytecodeExplorerCellRenderer extends DefaultTableCellRenderer {
    static final int TABLE_COLUMNS = 16;
    private static Color[] colors;

    //    private static Color[] backgroundColors = new Color[]{Color.pink, Color.yellow, Color.ORANGE, Color.LIGHT_GRAY, Color.white};
    private static final Color[] backgroundColors = new Color[]{Color.LIGHT_GRAY, Color.white};

    public BytecodeExplorerCellRenderer() {
    }

    /**
     * Call this once before instantiating any of these cell renderers
     */
    public static void populateColors(ClassFile classFile) {
        colors = new Color[classFile.length()];
        System.out.println("Colors.length:" + colors.length);
        int prevSectionIndex = -1;
        for (int i = 0; i < classFile.length(); i++) {
            int sectionIndex = classFile.getSectionIndex(i);
            colors[i] = backgroundColors[sectionIndex % backgroundColors.length];
            if (prevSectionIndex != sectionIndex) {
                prevSectionIndex = sectionIndex;
//                System.out.printf("Color(%h): %d%n", i, sectionIndex);
            }
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        int byteIndex = row * TABLE_COLUMNS + column;
        Color color;
        if(byteIndex >= PointSelector.getStartHighlightBlock() && byteIndex <= PointSelector.getEndHighlightBlock()) {
            color = Color.yellow;
        }
        else if (byteIndex < colors.length) {
            color = colors[byteIndex];
        }
        else {
            color = Color.white;
        }
        component.setBackground(color);
        return component;
    }
}
