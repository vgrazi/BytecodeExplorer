package com.vgrazi.bytecodeexplorer.ui.javafx;

import com.vgrazi.bytecodeexplorer.BytecodeExplorer;
import com.vgrazi.bytecodeexplorer.structure.ClassFile;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TableWithArrayData extends Application {

    private static final int NUM_COLS = 10;

    private BytecodeExplorer bytecodeExplorer;
    private Label instructionLabel = new Label();
    private int hoverByteIndex;
    ClassFile classFile;


    @Override
    public void start(Stage primaryStage) throws IOException {
        List<String> params = getParameters().getRaw();
        String classfile = params.get(0);
        bytecodeExplorer = new BytecodeExplorer(classfile);
        classFile = bytecodeExplorer.getClassFile();
        int length = classFile.length();
        int numRows = getRowCount(length);

        instructionLabel.setTextAlignment(TextAlignment.LEFT);
        instructionLabel.setStyle("-fx-background-color:yellow; -fx-pref-width:400px;");

        TableView<String[]> table = new TableView<>();

        ObservableList<String[]> data = FXCollections.observableArrayList();

        table.setItems(data);
        table.setMaxWidth(415);

        // set up the column headers
        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            String[] row = new String[NUM_COLS + 1];
            data.add(row);
            for (int colIndex = 0; colIndex < NUM_COLS + 1; colIndex++) {
                if (colIndex > 0) {
                    int index = rowIndex * NUM_COLS + colIndex - 1;
                    if (index >= classFile.length()) {
                        row[colIndex] = "";
                    } else {
                        row[colIndex] = String.format("%02X", classFile.getByte(index));
                    }
                } else {
                    row[colIndex] = String.valueOf(rowIndex * NUM_COLS);
                }
            }
        }

        IntegerProperty hoverRow = new SimpleIntegerProperty();
        IntegerProperty hoverCol = new SimpleIntegerProperty();

        for (int colIndex = 0; colIndex < NUM_COLS + 1; colIndex++) {
            TableColumn<String[], String> column;
            if (colIndex > 0) {
                column = new TableColumn<>(String.format("%1d", colIndex - 1));
            } else {
                column = new TableColumn<>("");
            }
            final int index = colIndex - 1;

            final int finalColIndex = colIndex;
            column.setCellValueFactory(cellData -> {
                return new ReadOnlyStringWrapper(cellData.getValue()[finalColIndex]);
            });

            column.setCellFactory(col -> {
                TableCell<String[], String> cell = new TableCell<String[], String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item);
                        }
                    }
                };
                cell.setOnMouseEntered(e -> {
                    hoverCol.set(index);
                    hoverRow.set(cell.getIndex());
                    hoverByteIndex = index + cell.getIndex() * NUM_COLS;
                    cell.setStyle("-fx-background-color: yellow");
                });
                cell.setOnMouseExited(e -> {
                    hoverCol.set(-1);
                    hoverRow.set(-1);
                    cell.setStyle("-fx-background-color: white");
                });

                return cell;
            });

            table.getColumns().add(column);
        }

        instructionLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            int col = hoverCol.get();
            int row = hoverRow.get();
            if (col < 0 || row < 0) {
                return "";
            } else {
                return renderBytes(col, row);
            }
        }, hoverCol, hoverRow));


        BorderPane root = new BorderPane(null, null, instructionLabel, null, table);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public String renderBytes(int col, int row) {

//        System.out.println("TableWithArrayData.start " + row + "," + col);

        // todo: render the instructionLabel here
        classFile.getInstructionSequence(hoverByteIndex);
        return String.format("Mouse is in [%3d, %3d] hoverIndex: %3d", col, row, hoverByteIndex) + "\nthanks";
    }

    /**
     * Uses the supplied length and fixed COLUMN_COUNT to determine the number of required rows
     *
     * @param length
     * @return
     */
    private int getRowCount(int length) {
        int rows = length / NUM_COLS;
        if (rows * NUM_COLS != length) {
            rows++;
        }
        return rows;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
