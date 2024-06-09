package com.cinema.main.views.movies;

import java.util.List;

import com.cinema.application.dtos.movies.DeleteMovieSessionDTO;
import com.cinema.application.dtos.movies.MovieSessionDTO;
import com.cinema.application.helpers.Response;
import com.cinema.main.factories.movies.DeleteMovieSessionFactory;
import com.cinema.main.factories.movies.ListMovieSessionsFactory;
import com.cinema.main.views.StageManager;
import com.cinema.main.views.helpers.AlertError;
import com.cinema.main.views.helpers.AlertSuccess;
import com.cinema.main.views.helpers.ButtonTableCell;
import com.cinema.main.views.helpers.ChangeWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ListMovieSessionsView {

  @FXML
  private TableColumn<MovieSessionDTO, Void> action;

  @FXML
  private TableColumn<MovieSessionDTO, String> cinemaHall;

  @FXML
  private TableColumn<MovieSessionDTO, String> movie;

  @FXML
  private TableView<MovieSessionDTO> movieSessionTable;

  @FXML
  private TableColumn<MovieSessionDTO, String> startTime;

  @FXML
  void initialize() {
    Response<?> response = ListMovieSessionsFactory.make().handle(null);

    Object data = response.getData();

    if (data instanceof List) {
      ObservableList<MovieSessionDTO> movieSessions = FXCollections.observableArrayList();

      for (Object movieSession : (List<?>) data) {
        if (movieSession instanceof MovieSessionDTO) {
          movieSessions.add((MovieSessionDTO) movieSession);
        }
      }

      movieSessionTable.setItems(movieSessions);
    }

    movie.setCellValueFactory(new PropertyValueFactory<>("movie"));
    movie.setStyle("-fx-alignment: CENTER;");

    cinemaHall.setCellValueFactory(new PropertyValueFactory<>("cinemaHall"));
    cinemaHall.setStyle("-fx-alignment: CENTER;");

    startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
    startTime.setStyle("-fx-alignment: CENTER;");

    action.setCellFactory(column -> new ButtonTableCell<>("Excluir", this::deleteMovieSession));
  }

  private void deleteMovieSession(MovieSessionDTO movieSession) {
    showConfirmationDialog(movieSession);
  }

  private void showConfirmationDialog(MovieSessionDTO movieSession) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmação de Exclusão");
    alert.setHeaderText(null);
    alert.setContentText("Deseja realmente excluir a sessão do filme " + movieSession.getMovie() + " no horário de "
        + movieSession.getStartTime() + " na sala " + movieSession.getCinemaHall() + "?");

    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        DeleteMovieSessionDTO deleteMovieSessionDTO = new DeleteMovieSessionDTO(movieSession.getID());

        Response<?> deleteResponse = DeleteMovieSessionFactory.make().handle(deleteMovieSessionDTO);

        if (deleteResponse.getStatusCode() == 204) {
          new AlertSuccess("Sessão deletada com sucesso!");
          movieSessionTable.getItems().remove(movieSession);
        } else {
          new AlertError(deleteResponse.getData().toString());
        }
      }
    });

  }

  @FXML
  void createSession(MouseEvent event) throws Exception {
    Stage primaryStage = StageManager.getPrimaryStage();

    ChangeWindow.changeScene(primaryStage, "/com/cinema/main/views/movies/createMovieSession.fxml");
  }

}
