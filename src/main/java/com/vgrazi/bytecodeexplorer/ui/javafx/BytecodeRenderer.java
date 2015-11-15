package com.vgrazi.bytecodeexplorer.ui.javafx;

import com.vgrazi.bytecodeexplorer.BytecodeExplorer;
import com.vgrazi.bytecodeexplorer.structure.ClassFile;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by vgrazi on 8/14/15.
 */
public class BytecodeRenderer {
    private static final int COLUMN_COUNT = 20;
    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel();
    private JTable table;

    public static void main(String[] args) throws IOException {
        new BytecodeRenderer().launch(args[0]);
    }

    public void launch(String classfile) throws IOException {
        BytecodeExplorer bytecodeExplorer = new BytecodeExplorer(classfile);
        ClassFile classFile = bytecodeExplorer.getClassFile();
        int length = classFile.length();
        int rows = getRowCount(length);
        createTable(rows);
        panel.add(table);
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setBounds(100, 100, 800, 600);
    }

    private void createTable(int rows) {
        table = new JTable(rows, COLUMN_COUNT + 1) {
            public TableCellRenderer getCellRenderer(int row, int column) {
                return new TableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        String text;
                        if (column > 0) {
                            text = String.format("%02X", row) + "." + String.format("%02X", column - 1);
                        }
                        else {
                            text = String.valueOf(row);
                        }
                        final JLabel jLabel = new JLabel(text);
                        jLabel.addMouseMotionListener(new MouseInputAdapter() {
                            /**
                             * {@inheritDoc}
                             *
                             * @param e
                             */
                            @Override
                            public void mouseEntered(MouseEvent e) {
                                jLabel.setBackground(Color.red);
                            }

                            /**
                             * {@inheritDoc}
                             *
                             * @param e
                             */
                            @Override
                            public void mouseExited(MouseEvent e) {
                                jLabel.setBackground(Color.white);
                            }
                        });
                        return jLabel;
                    }
                };
            }
        };
        for (int i = 0; i < COLUMN_COUNT; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(40);
        }
    }

    /**
     * Uses the supplied length and fixed COLUMN_COUNT to determine the number of required rows
     *
     * @param length
     * @return
     */
    private int getRowCount(int length) {
        int rows = length / COLUMN_COUNT;
        if (rows * length != COLUMN_COUNT) {
            rows++;
        }
        return rows;
    }
}
