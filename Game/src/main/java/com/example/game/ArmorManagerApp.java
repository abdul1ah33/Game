package com.example.game;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class ArmorManagerApp extends Application {

    private TableView<Armor> table = new TableView<>();
    private ObservableList<Armor> armorData = FXCollections.observableArrayList();

    // Input fields
    private TextField nameInput = new TextField();
    private TextField damageHeaderInput = new TextField(); // Renamed variable to avoid confusion, though logic is damageNegation
    private TextField damageInput = new TextField();
    private TextField rarityInput = new TextField();
    private TextField effectInput = new TextField();
    private TextField weightInput = new TextField();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Armor Manager");

        // TableView Columns
        TableColumn<Armor, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Armor, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Armor, Integer> damageCol = new TableColumn<>("Damage Negation");
        damageCol.setCellValueFactory(new PropertyValueFactory<>("damageNegation"));

        TableColumn<Armor, String> rarityCol = new TableColumn<>("Rarity");
        rarityCol.setCellValueFactory(new PropertyValueFactory<>("rarity"));

        TableColumn<Armor, String> effectCol = new TableColumn<>("Special Effect");
        effectCol.setCellValueFactory(new PropertyValueFactory<>("specialEffect"));

        TableColumn<Armor, Double> weightCol = new TableColumn<>("Weight");
        weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));

        table.getColumns().addAll(idCol, nameCol, damageCol, rarityCol, effectCol, weightCol);
        table.setItems(armorData);
        
        // Listen for selection to populate fields for update/delete
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameInput.setText(newSelection.getName());
                damageInput.setText(String.valueOf(newSelection.getDamageNegation()));
                rarityInput.setText(newSelection.getRarity());
                effectInput.setText(newSelection.getSpecialEffect());
                weightInput.setText(String.valueOf(newSelection.getWeight()));
            }
        });

        // Input Form
        GridPane inputGrid = new GridPane();
        inputGrid.setPadding(new Insets(10));
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);

        inputGrid.add(new Label("Name:"), 0, 0);
        inputGrid.add(nameInput, 1, 0);
        inputGrid.add(new Label("Damage Negation:"), 0, 1);
        inputGrid.add(damageInput, 1, 1);
        inputGrid.add(new Label("Rarity:"), 0, 2);
        inputGrid.add(rarityInput, 1, 2);
        inputGrid.add(new Label("Special Effect:"), 0, 3);
        inputGrid.add(effectInput, 1, 3);
        inputGrid.add(new Label("Weight:"), 0, 4);
        inputGrid.add(weightInput, 1, 4);

        // Buttons
        Button addButton = new Button("Add Armor");
        addButton.setOnAction(e -> addArmor());

        Button updateButton = new Button("Update Selected");
        updateButton.setOnAction(e -> updateArmor());

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> deleteArmor());

        Button refreshButton = new Button("Refresh Data");
        refreshButton.setOnAction(e -> loadData());

        Button clearButton = new Button("Clear Fields");
        clearButton.setOnAction(e -> clearFields());

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, refreshButton, clearButton);
        buttonBox.setPadding(new Insets(10));

        VBox layout = new VBox(10, table, inputGrid, buttonBox);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        loadData(); // Load initial data
    }

    // CREATE
    private void addArmor() {
        String insertSQL = "INSERT INTO armors (ArmorName, ArmorDamageNegation, ArmorRarity, ArmorSpecialEffect, Weight) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, nameInput.getText());
            pstmt.setInt(2, Integer.parseInt(damageInput.getText()));
            pstmt.setString(3, rarityInput.getText());
            pstmt.setString(4, effectInput.getText());
            pstmt.setDouble(5, Double.parseDouble(weightInput.getText()));

            pstmt.executeUpdate();
            loadData();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Armor added successfully!");

        } catch (SQLException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not add armor: " + e.getMessage());
        }
    }

    // READ
    private void loadData() {
        armorData.clear();
        String query = "SELECT * FROM armors";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                armorData.add(new Armor(
                        rs.getInt("ArmorID"),
                        rs.getString("ArmorName"),
                        rs.getInt("ArmorDamageNegation"),
                        rs.getString("ArmorRarity"),
                        rs.getString("ArmorSpecialEffect"),
                        rs.getDouble("Weight")
                ));
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not load data: " + e.getMessage());
        }
    }

    // UPDATE
    private void updateArmor() {
        Armor selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an armor to update.");
            return;
        }

        String updateSQL = "UPDATE armors SET ArmorName=?, ArmorDamageNegation=?, ArmorRarity=?, ArmorSpecialEffect=?, Weight=? WHERE ArmorID=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, nameInput.getText());
            pstmt.setInt(2, Integer.parseInt(damageInput.getText()));
            pstmt.setString(3, rarityInput.getText());
            pstmt.setString(4, effectInput.getText());
            pstmt.setDouble(5, Double.parseDouble(weightInput.getText()));
            pstmt.setInt(6, selected.getId());

            pstmt.executeUpdate();
            loadData();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Armor updated successfully!");

        } catch (SQLException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not update armor: " + e.getMessage());
        }
    }

    // DELETE
    private void deleteArmor() {
        Armor selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an armor to delete.");
            return;
        }

        String deleteSQL = "DELETE FROM armors WHERE ArmorID=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setInt(1, selected.getId());
            pstmt.executeUpdate();
            loadData();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Armor deleted successfully!");

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not delete armor: " + e.getMessage());
        }
    }

    private void clearFields() {
        nameInput.clear();
        damageInput.clear();
        rarityInput.clear();
        effectInput.clear();
        weightInput.clear();
        table.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
