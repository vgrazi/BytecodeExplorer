package com.vgrazi.bytecodeexplorer.ui.javafx;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.Map;

public class TableViewMap extends Application {

    public static final String Column1MapKey = "A";
    public static final String Column2MapKey = "B";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(500);
        stage.setHeight(500);

        final Label label = new Label("Student IDs");
        label.setFont(new Font("Arial", 20));

        TableColumn<Map, String> firstDataColumn = new TableColumn<>("Class A");
        TableColumn<Map, String> secondDataColumn = new TableColumn<>("Class B");

        firstDataColumn.setCellValueFactory(new MapValueFactory(Column1MapKey));
        firstDataColumn.setMinWidth(130);

        secondDataColumn.setCellValueFactory(new MapValueFactory(Column2MapKey));
        secondDataColumn.setMinWidth(130);

        TableView table = new TableView<>();

        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.getColumns().setAll(firstDataColumn, secondDataColumn);
        Callback<TableColumn<Map, String>, TableCell<Map, String>> cellFactoryForMap = p -> new TextFieldTableCell(new StringConverter() {
            @Override
            public String toString(Object t) {
                if (t != null) {
                    return t.toString();
                }
                return "";
            }

            @Override
            public Object fromString(String string) {
                return string;
            }
        });

        firstDataColumn.setCellFactory(cellFactoryForMap);
        secondDataColumn.setCellFactory(cellFactoryForMap);

        Button displayButton = new Button("Display");
        Button addButton = new Button("Add Column");
        addButton.setOnAction(actionEvent -> {
            TableColumn miscTableColumn = new TableColumn("misc");
            String mapKey = "C";
            miscTableColumn.setCellValueFactory(new MapValueFactory(mapKey));
            miscTableColumn.setMinWidth(130);
            miscTableColumn.setCellFactory(cellFactoryForMap);


            table.getColumns().add(miscTableColumn);

        });

        displayButton.setOnAction(actionEvent -> displayColumns(table));

        final VBox vbox = new VBox();

        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, displayButton, addButton, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);

        stage.show();
    }

    private ObservableList<Map<String, String>> generateDataInMap() {
        int max = 10;
        ObservableList<Map<String, String>> allData = FXCollections.observableArrayList();
        for (int i = 1; i < max; i++) {
            Map<String, String> dataRow = new HashMap<>();
            String value1 = "A" + i;
            dataRow.put(Column1MapKey, value1);

            String value2 = "B" + i;
            dataRow.put(Column2MapKey, value2);

            allData.add(dataRow);
        }
        return allData;
    }

    private void displayColumns(TableView table) {
        System.out.println("==============");
        ObservableList<TableColumn> columns = table.getColumns();
        final int[] columnCount = {0};
        columns.forEach(column ->
        {
            System.out.println(column.getText());
            for (int j = 0; j < table.getItems().size(); j++) {
                ObservableValue<?> value2 = column.getCellObservableValue(j);
                Object value1 = null;
                if (value2 != null) {
                    value1 = value2.getValue();
                }
                System.out.println("Cell: " + (columnCount[0] + 1) + "," + (j + 1) + " '" + value1 + "'");
            }
            columnCount[0]++;
            System.out.println();
        });
    }

}