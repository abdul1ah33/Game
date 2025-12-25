package com.example.game;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;

public class DynamicTablePane extends BorderPane {

    private final Schema.TableDef tableDef;
    private final DatabaseManager dbManager;
    private final TableView<Map<String, Object>> tableView;
    private final ObservableList<Map<String, Object>> dataList;
    

    private final Map<String, Control> inputFields = new HashMap<>();

    public DynamicTablePane(Schema.TableDef tableDef) {
        this.tableDef = tableDef;
        this.dbManager = new DatabaseManager();
        this.dataList = FXCollections.observableArrayList();
        this.tableView = new TableView<>();

        initializeUI();
        refreshData();
    }

    private void initializeUI() {

        for (Schema.ColumnDef colDef : tableDef.columns()) {
            TableColumn<Map<String, Object>, Object> col = new TableColumn<>(colDef.name());
            col.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().get(colDef.name())));
            tableView.getColumns().add(col);
        }
        tableView.setItems(dataList);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) populateForm(newVal);
        });

        setCenter(tableView);


        GridPane formGrid = new GridPane();
        formGrid.setPadding(new Insets(15));
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.getStyleClass().add("form-container");

        int row = 0;
        int col = 0;

        for (Schema.ColumnDef colDef : tableDef.columns()) {
            Label label = new Label(colDef.name());
            label.setStyle("-fx-text-fill: #cdd6f4;");
            
            Control input;
            
            if (colDef.fkTable() != null) {

                ComboBox<LookupItem> comboBox = new ComboBox<>();
                comboBox.setPromptText("Select " + colDef.name());

                loadLookupData(comboBox, colDef);
                input = comboBox;
            } else {
                TextField tf = new TextField();
                tf.setPromptText(colDef.type());
                if (colDef.isAutoIncrement()) {
                    tf.setDisable(true);
                    tf.setPromptText("Auto Generated");
                }
                input = tf;
            }

            inputFields.put(colDef.name(), input);

            formGrid.add(label, col * 2, row);
            formGrid.add(input, col * 2 + 1, row);


            col++;
            if (col > 1) {
                col = 0;
                row++;
            }
        }


        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(15, 0, 0, 0));
        
        Button btnAdd = new Button("Add New");
        btnAdd.getStyleClass().add("action-button");
        btnAdd.setOnAction(e -> handleAdd());

        Button btnUpdate = new Button("Update Selected");
        btnUpdate.getStyleClass().add("action-button");
        btnUpdate.setOnAction(e -> handleUpdate());

        Button btnDelete = new Button("Delete Selected");
        btnDelete.getStyleClass().add("delete-button");
        btnDelete.setOnAction(e -> handleDelete());

        Button btnClear = new Button("Clear Form");
        btnClear.getStyleClass().add("secondary-button");
        btnClear.setOnAction(e -> clearForm());

        buttonBox.getChildren().addAll(btnAdd, btnUpdate, btnDelete, btnClear);
        
        VBox bottomContainer = new VBox(10, formGrid, buttonBox);
        bottomContainer.setPadding(new Insets(20));
        setBottom(bottomContainer);
    }

    private void loadLookupData(ComboBox<LookupItem> comboBox, Schema.ColumnDef colDef) {
        Map<String, String> lookup = dbManager.fetchLookup(colDef.fkTable(), colDef.fkIdCol(), colDef.fkDisplayCol());
        ObservableList<LookupItem> items = FXCollections.observableArrayList();
        lookup.forEach((id, name) -> items.add(new LookupItem(id, name)));
        comboBox.setItems(items);
    }

    private void refreshData() {
        dataList.clear();
        dataList.addAll(dbManager.fetchAll(tableDef.tableName()));
    }

    private void handleAdd() {
        try {
            Map<String, Object> data = collectFormData();

            for (Schema.ColumnDef c : tableDef.columns()) {
                if (c.isAutoIncrement()) data.remove(c.name());
            }
            
            dbManager.insert(tableDef.tableName(), data);
            refreshData();
            clearForm();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Record added.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void handleUpdate() {
        Map<String, Object> selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Select a row to update.");
            return;
        }

        try {
            Map<String, Object> data = collectFormData();
            Map<String, Object> keys = getKeyMap(selected);
            
            dbManager.update(tableDef.tableName(), data, keys);
            refreshData();
            clearForm();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Record updated.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void handleDelete() {
        Map<String, Object> selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Select a row to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this record?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.YES) {
            try {
                Map<String, Object> keys = getKeyMap(selected);
                dbManager.delete(tableDef.tableName(), keys);
                refreshData();
                clearForm();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }

    // this part is hard
    private Map<String, Object> collectFormData() throws Exception {
        Map<String, Object> data = new HashMap<>();
        
        // checking every column
        List<Schema.ColumnDef> cols = tableDef.columns();
        for (int i = 0; i < cols.size(); i++) {
            Schema.ColumnDef col = cols.get(i);
            String name = col.name();
            Control input = inputFields.get(name);
            Object value = null;

            if (input instanceof TextField) {
                TextField tf = (TextField) input;
                String text = tf.getText();
                // if (!text.isEmpty()) {
                //     value = text;
                // }
                if (text.isEmpty() && !col.isAutoIncrement()) {
                     if (col.type().equals("INT") || col.type().equals("DECIMAL")) {
                         value = null;
                     } else {
                         value = text;
                     }
                } else if (!text.isEmpty()) {
                     value = text;
                }
            } else if (input instanceof ComboBox) {
                // casting is annoying
                ComboBox cb = (ComboBox) input;
                Object sel = cb.getValue();
                if (sel != null) {
                    LookupItem item = (LookupItem) sel;
                    value = item.id;
                }
            }

            if (value != null || !col.isAutoIncrement()) {
                data.put(name, value);
            }
        }
        return data;
    }

    private Map<String, Object> getKeyMap(Map<String, Object> row) {
        Map<String, Object> keys = new HashMap<>();
        for (Schema.ColumnDef col : tableDef.columns()) {
            if (col.isPrimary()) {
                keys.put(col.name(), row.get(col.name()));
            }
        }
        return keys;
    }

    private void populateForm(Map<String, Object> row) {
        for (Schema.ColumnDef col : tableDef.columns()) {
            Control input = inputFields.get(col.name());
            Object val = row.get(col.name());

            if (input instanceof TextField tf) {
                tf.setText(val != null ? val.toString() : "");
            } else if (input instanceof ComboBox<?> cb) {

                @SuppressWarnings("unchecked")
                ComboBox<LookupItem> combo = (ComboBox<LookupItem>) input;
                if (val != null) {
                    String idStr = val.toString();
                    for (LookupItem item : combo.getItems()) {
                        if (item.id.equals(idStr)) {
                            combo.setValue(item);
                            break;
                        }
                    }
                } else {
                    combo.getSelectionModel().clearSelection();
                }
            }
        }
    }

    private void clearForm() {
        tableView.getSelectionModel().clearSelection();
        inputFields.forEach((k, v) -> {
            if (v instanceof TextField tf) tf.clear();
            if (v instanceof ComboBox<?> cb) cb.getSelectionModel().clearSelection();
        });
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setContentText(content);
        a.setHeaderText(null);
        a.show();
    }


    private static class LookupItem {
        String id;
        String name;

        public LookupItem(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name + " (ID: " + id + ")";
        }
    }
}
