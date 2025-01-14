package org.example.collaborative_task_management_application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class demoKanban extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root layout
        HBox kanbanBoard = new HBox(10);
        kanbanBoard.setPadding(new Insets(10));

        // ScrollPane for horizontal scrolling
        ScrollPane scrollPane = new ScrollPane(kanbanBoard);
        scrollPane.setFitToHeight(true);

        // Add initial columns
        kanbanBoard.getChildren().addAll(createColumn("To Do"), createColumn("In Progress"), createColumn("Done"));

        // Add button to add new columns
        Button addColumnButton = new Button("Add Column");
        addColumnButton.setOnAction(e -> {
            kanbanBoard.getChildren().add(createColumn("New Column"));
        });
        HBox.setMargin(addColumnButton, new Insets(10, 0, 0, 10));

        // Root layout
        VBox root = new VBox(10, scrollPane, addColumnButton);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Advanced Kanban Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createColumn(String columnName) {
        VBox column = new VBox(10);
        column.setPadding(new Insets(10));
        column.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
        column.setPrefWidth(200);

        Label title = new Label(columnName);
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        column.getChildren().add(title);

        Button addTaskButton = new Button("+ Add Task");
        addTaskButton.setOnAction(e -> {
            column.getChildren().add(createTaskCard("New Task"));
        });
        column.getChildren().add(addTaskButton);

        return column;
    }

    private HBox createTaskCard(String taskName) {
        HBox taskCard = new HBox(10);
        taskCard.setPadding(new Insets(5));
        taskCard.setStyle("-fx-background-color: white; -fx-border-color: black;");

        Label taskLabel = new Label(taskName);
        taskCard.getChildren().add(taskLabel);

        // Add drag-and-drop functionality
        taskCard.setOnDragDetected(event -> {
            taskCard.startDragAndDrop(TransferMode.MOVE).setContent(null);
            event.consume();
        });

        taskCard.setOnDragOver(event -> {
            if (event.getGestureSource() != taskCard) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        taskCard.setOnDragDropped(event -> {
            ((VBox) taskCard.getParent()).getChildren().remove(taskCard);
            ((VBox) event.getGestureTarget()).getChildren().add(taskCard);
            event.setDropCompleted(true);
            event.consume();
        });

        return taskCard;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
