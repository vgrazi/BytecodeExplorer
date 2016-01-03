package com.vgrazi.bytecodeexplorer.ui.swing;

import com.vgrazi.bytecodeexplorer.structure.ClassAccessFlagsSection;
import com.vgrazi.bytecodeexplorer.structure.ClassFile;
import com.vgrazi.bytecodeexplorer.structure.ClassFileSection;
import com.vgrazi.bytecodeexplorer.structure.CountSection;

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
     * Alternates block colors, except for count and class access flags sections, which are pink
     */
    public static void populateColors(ClassFile classFile) {
        colors = new Color[classFile.length()];
        System.out.println("Colors.length:" + colors.length);
        int prevSectionIndex = -1;
        for (int i = 0; i < classFile.length(); i++) {
            int sectionIndex = classFile.getSectionIndex(i);
            ClassFileSection section = classFile.getSection(i);
            if(section instanceof CountSection) {
                colors[i] = Color.pink;
            }
            else if(section instanceof ClassAccessFlagsSection) {
                colors[i] = Color.pink;
            }
            else {
                colors[i] = backgroundColors[sectionIndex % backgroundColors.length];
            }
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
