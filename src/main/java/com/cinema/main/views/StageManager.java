package com.cinema.main.views;

import javafx.stage.Stage;

public class StageManager {
  private static Stage primaryStage;

  public static void setPrimaryStage(Stage stage) {
    primaryStage = stage;
  }

  public static Stage getPrimaryStage() {
    return primaryStage;
  }
}