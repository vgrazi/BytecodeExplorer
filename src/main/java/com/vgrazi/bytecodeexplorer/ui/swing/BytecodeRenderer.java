package com.vgrazi.bytecodeexplorer.ui.swing;

import com.vgrazi.bytecodeexplorer.structure.ClassFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class BytecodeRenderer {

    public static final int TABLE_WIDTH_PX = 450;
    private final JLabel instructionPanel = new JLabel();

    // construct the hex table
    private JTable hexTable = new JTable();
    // construct the frame
    private JFrame frame = createFrame();


//    private int startHighlightBlock = -1;
//    private int endHighlightBlock = -1;


    private final ClassFile classFile;

    public BytecodeRenderer(ClassFile classFile) {
        this.classFile = classFile;
        launch();
    }

    public void launch() {
        hexTable.setEnabled(false);
        hexTable.getTableHeader().setResizingAllowed(false);

        // construct the row labels
        JTable rowLabelTable = new JTable();
        addDataToHexTable(hexTable, rowLabelTable);

        addInstructionRenderer(hexTable);

        // add the hex table and row labels to the frame
        addHexTable(frame, hexTable, rowLabelTable);

        // add the instruction panel to the frame
        addInstructionPanel(frame);
        rerender(frame);
    }

    private void rerender(JFrame frame) {
        // refresh the layout
        frame.validate();
        frame.repaint();

    }

    private void addInstructionRenderer(JTable hexTable) {
        InstructionRenderer instructionRenderer = new InstructionRenderer(classFile, instructionPanel);
        PointSelector pointSelector = new PointSelector(instructionRenderer, hexTable, classFile);
        hexTable.addMouseListener(pointSelector);
        hexTable.addMouseMotionListener(pointSelector);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Bytecode Explorer");
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
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int pos = 50;
        frame.setBounds(pos, pos, screenSize.width - pos * 2, screenSize.height - pos * 2);
        frame.setVisible(true);
        return frame;
    }

    private void addInstructionPanel(JFrame frame) {
        instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
        instructionPanel.setVerticalAlignment(SwingConstants.TOP);

        instructionPanel.setBackground(Color.YELLOW);
        final JScrollPane instructionScrollPane = new JScrollPane();
        instructionScrollPane.setViewportView(instructionPanel);
        frame.getContentPane().add(instructionScrollPane);
    }

    private void addHexTable(JFrame frame, JTable hexTable, JTable rowLabelTable) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(TABLE_WIDTH_PX, 100));
        panel.add(BorderLayout.WEST, rowLabelTable);
        panel.add(BorderLayout.CENTER, hexTable);

        JScrollPane hexScrollPane = new JScrollPane();
        hexScrollPane.getViewport().setView(hexTable);

        // our layout manager expects the hex scrollpane to be the first component, so add it at position 0
        frame.getContentPane().add(hexScrollPane, 0);

        rowLabelTable.setEnabled(false);

        JTableHeader header = hexTable.getTableHeader();
        rowLabelTable.setBackground(header.getBackground());
        rowLabelTable.setFont(header.getFont());

        JViewport viewport = new JViewport();
        viewport.setView(rowLabelTable);
        viewport.setPreferredSize(new Dimension(45, rowLabelTable.getPreferredSize().height));
        hexScrollPane.setRowHeaderView(viewport);
        JLabel corner = new JLabel("Row");
        corner.setFont(header.getFont());
        hexScrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, corner);
        BytecodeExplorerCellRenderer.populateColors(classFile);
        for (int i = 0; i < BytecodeExplorerCellRenderer.TABLE_COLUMNS; i++) {
            hexTable.getColumnModel().getColumn(i).setCellRenderer(new BytecodeExplorerCellRenderer());
        }
    }

    /**
     * Populates the hex table and row numbers
     */
    private void addDataToHexTable(JTable hexTable, JTable rowLabelTable) {
        byte[] bytes = classFile.getBytes();
        int rows = bytes.length / BytecodeExplorerCellRenderer.TABLE_COLUMNS;

        if (rows * BytecodeExplorerCellRenderer.TABLE_COLUMNS < bytes.length) {
            // make room for remaining bytes
            rows = rows + 1;
        }

        // first set the column labels
        Object[] names = new Object[BytecodeExplorerCellRenderer.TABLE_COLUMNS];
        for (int i = 0; i < names.length; i++) {
            names[i] = String.format("%01X", i);
        }

        // Next set the data
        Object[][] data = new Object[rows][BytecodeExplorerCellRenderer.TABLE_COLUMNS];
        for (int i = 0; i < bytes.length; i++) {
            int row = i / BytecodeExplorerCellRenderer.TABLE_COLUMNS;
            int col = i % BytecodeExplorerCellRenderer.TABLE_COLUMNS;
            data[row][col] = String.format("%02X ", bytes[i]);
        }

        hexTable.setModel(new DefaultTableModel(data, names));

        // Now set the row labels
        Object[][] rowLabelData = new Object[hexTable.getRowCount()][1];
        for (int i = 0; i < hexTable.getRowCount(); i++) {
            String formatted = String.format("%04X", i * 16);
            rowLabelData[i][0] = formatted;
        }

        rowLabelTable.setModel(new DefaultTableModel(rowLabelData, new String[]{"Row"}));
    }
}
