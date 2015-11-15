package com.vgrazi.bytecodeexplorer.ui.swing;

import com.vgrazi.bytecodeexplorer.structure.ClassFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class BytecodeRenderer {

    private static final int TABLE_COLUMNS = 16;
    public static final int TABLE_WIDTH_PX = 450;

    private final JPanel instructionPanel = new JPanel();
    private final JScrollPane hexScrollPane = new JScrollPane();
    private final JFrame frame = new JFrame("Bytecode Explorer");


    private final ClassFile classFile;

    public BytecodeRenderer(ClassFile classFile) {
        this.classFile = classFile;
        launch();
    }

    public void launch() {
        JTable hexTable = new JTable();
        hexTable.getTableHeader().setResizingAllowed(false);
        JTable rowLabelTable = new JTable();
        String[] names = new String[]{"Rows"};
        rowLabelTable.setModel(new DefaultTableModel(new Object[1][0], names));

        instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
        JFrame frame = createFrame();

        addHexTable(frame, hexTable, rowLabelTable);
        addInstructionPanel(frame, instructionPanel);

        addData(hexTable, rowLabelTable);
    }

    private JFrame createFrame() {
        frame.getContentPane().setLayout(new FlowLayout() {
            @Override
            public void layoutContainer(Container target) {
                int delta = 2;
                int count = target.getComponentCount();
                for (int i = 0; i < count; i++) {
                    Component component = target.getComponent(i);
                    switch (i) {
                        case 0:
                            component.setBounds(0, 0, TABLE_WIDTH_PX, target.getHeight());
                            break;
                        case 1:
                        component.setBounds(TABLE_WIDTH_PX + delta, 0, target.getWidth() - TABLE_WIDTH_PX - delta, target.getHeight());
                        break;
                    }
                }
            }
        });
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(10, 10, 1200, 600);
        frame.setVisible(true);
        return frame;
    }

    private void addInstructionPanel(JFrame frame, JPanel instructionPanel) {
        instructionPanel.setBackground(Color.YELLOW);
        JScrollPane instructionScrollPane = new JScrollPane(instructionPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        frame.getContentPane().add(instructionScrollPane);
        frame.getContentPane().doLayout();
    }

    private void addHexTable(JFrame frame, JTable hexTable, JTable rowLabels) {
//        hexTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(TABLE_WIDTH_PX, 100));
        panel.add(BorderLayout.WEST, rowLabels);
        panel.add(BorderLayout.CENTER, hexTable);

        hexScrollPane.getViewport().setView(hexTable);
        frame.getContentPane().add(hexScrollPane);
        hexTable.setBackground(Color.cyan);
        frame.getContentPane().doLayout();
    }

    private void addData(JTable hexTable, JTable rowLabelTable) {
        byte[] bytes = classFile.getBytes();
        int rows = bytes.length/ TABLE_COLUMNS;

        if(rows * TABLE_COLUMNS < bytes.length) {
            // make room for remaining bytes
            rows = rows + 1;
        }


        // first set the names
        Object[] names = new Object[TABLE_COLUMNS];
        for(int i = 0; i < names.length; i++) {
            names[i] = String.format("%02X", i);
        }

        // Next set the data
        Object[][] data = new Object[rows][TABLE_COLUMNS];
        for(int i = 0; i < bytes.length; i++) {
            int row = i / TABLE_COLUMNS;
            int col = i % TABLE_COLUMNS;
            data[row][col] = String.format("%02X ", bytes[i]);
        }

        hexTable.setModel(new DefaultTableModel(data, names));

        // Now set the row labels
        Object[][] rowLabelData = new Object[hexTable.getRowCount()][1];
        for(int i = 0; i < hexTable.getRowCount(); i++) {
            String formatted = String.format("%02X", i);
            rowLabelData[i][0] = formatted;
        }
        rowLabelTable.setModel(new DefaultTableModel(rowLabelData, new String[]{"Row"}));
        rowLabelTable.setEnabled(false);

        JTableHeader header = hexTable.getTableHeader();
        rowLabelTable.setBackground(header.getBackground());
        rowLabelTable.setFont(header.getFont());

        JViewport viewport = new JViewport();
        viewport.setView(rowLabelTable);
        viewport.setPreferredSize(new Dimension(27, rowLabelTable.getPreferredSize().height));
        hexScrollPane.setRowHeaderView(viewport);
        JLabel corner = new JLabel("Row");
        corner.setFont(header.getFont());
        hexScrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, corner);

        for(int i = 0; i < TABLE_COLUMNS; i++) {
            hexTable.getColumnModel().getColumn(i).setCellRenderer(new BytecodeExplorerCellRenderer());
        }

        Component cellRenderer = hexTable.getCellRenderer(2, 2)
            .getTableCellRendererComponent(hexTable, "xxx", false, false, 2, 2);

    }

    @SuppressWarnings("SpellCheckingInspection")
    public void rerender(int i) {
        instructionPanel.removeAll();
        for (int j = 0; j < 10; j++) {
            instructionPanel.add(new JLabel(i + "," + j));
        }
        instructionPanel.doLayout();
    }
}
