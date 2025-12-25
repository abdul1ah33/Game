package com.example.game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MainDashboard extends Application {

    private BorderPane root;
    private Label activeTitle;

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        root.getStyleClass().add("root");


        VBox sidebar = new VBox();
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(250);

        Label title = new Label("GAME DB MANAGER");
        title.getStyleClass().add("sidebar-title");
        sidebar.getChildren().add(title);


        // making the loop for buttons
        List<Schema.TableDef> tables = Schema.getTables();
        for (int i = 0; i < tables.size(); i++) {
            Schema.TableDef t = tables.get(i);
            Button btn = new Button(t.displayName());
            btn.setMaxWidth(1000); // big width
            btn.getStyleClass().add("nav-button");
            
            // System.out.println("Adding button: " + t.displayName());
            
            btn.setOnAction(e -> {
                loadTable(t);
            });
            
            sidebar.getChildren().add(btn);
        }

        root.setLeft(sidebar);


        VBox welcomeBox = new VBox(20);
        welcomeBox.setPadding(new Insets(50));
        Label welcome = new Label("Select a Table to Manage");
        welcome.setStyle("-fx-text-fill: #cdd6f4; -fx-font-size: 24px;");
        welcomeBox.getChildren().add(welcome);
        root.setCenter(welcomeBox);

        Scene scene = new Scene(root, 1100, 750);
        

        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setTitle("Game Database Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadTable(Schema.TableDef table) {

        DynamicTablePane view = new DynamicTablePane(table);
        view.getStyleClass().add("content-area");
        


        root.setCenter(view);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
