package com.vgrazi.bytecodeexplorer.ui.swing;

import com.vgrazi.bytecodeexplorer.structure.ClassFile;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by vgrazi on 11/14/15.
 */
public class BytecodeExplorerCellRenderer extends DefaultTableCellRenderer {
    static final int TABLE_COLUMNS = 16;
    private BytecodeRenderer bytecodeRenderer;
    private ClassFile classFile;
    private static Color[] colors;

    //    private static Color[] backgroundColors = new Color[]{Color.pink, Color.yellow, Color.ORANGE, Color.LIGHT_GRAY, Color.white};
    private static final Color[] backgroundColors = new Color[]{Color.LIGHT_GRAY, Color.white};

    public BytecodeExplorerCellRenderer(BytecodeRenderer bytecodeRenderer, ClassFile classFile) {
        this.bytecodeRenderer = bytecodeRenderer;

        this.classFile = classFile;
    }

    /**
     * Call this once before instantiating any of these cell renderers
     * @param classFile
     */
    public static void populateColors(ClassFile classFile) {
        colors = new Color[classFile.length()];
        System.out.println("Colors.length:" + colors.length);
        for (int i = 0; i < classFile.length(); i++) {
            int sectionIndex = classFile.getSectionIndex(i);
            colors[i] = backgroundColors[sectionIndex % backgroundColors.length];
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        int byteIndex = row * TABLE_COLUMNS + column;
        Color color;
        if(byteIndex >= bytecodeRenderer.getStartHoverBlock() && byteIndex <= bytecodeRenderer.getEndHoverBlock()) {
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
