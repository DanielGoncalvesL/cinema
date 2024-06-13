package com.cinema.main.views.movies;

import com.cinema.application.dtos.movies.CinemaHallDTO;
import com.cinema.application.dtos.movies.UpdateCinemaHallDTO;
import com.cinema.application.helpers.Response;
import com.cinema.main.factories.movies.UpdateCinemaHallFactory;
import com.cinema.main.views.StageManager;
import com.cinema.main.views.helpers.AlertError;
import com.cinema.main.views.helpers.AlertSuccess;
import com.cinema.main.views.helpers.ChangeWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditCinemaHallView {
  @FXML
  private TextField name;

  @FXML
  private TextField capacity;

  private CinemaHallDTO cinemaHall;

  @FXML
  void initialize() {
    cinemaHall = CinemaHallModel.getInstance().getCinemaHall();

    name.setText(cinemaHall.getName());
    capacity.setText(String.valueOf(cinemaHall.getCapacity()));
  }

  @FXML
  void editCinemaHall(ActionEvent event) throws Exception{
    UpdateCinemaHallDTO updateCinemaHallDTO = new UpdateCinemaHallDTO(cinemaHall.getID(), name.getText(),
        Integer.parseInt(capacity.getText()));

    Response<?> response = UpdateCinemaHallFactory.make().handle(updateCinemaHallDTO);

    if (response.getStatusCode() == 204) {
      new AlertSuccess("Sala de cinema editada com sucesso");

      Stage primaryStage = StageManager.getPrimaryStage();

      ChangeWindow.changeScene(primaryStage, "/com/cinema/main/views/movies/listCinemaHalls.fxml");
    } else {
      new AlertError(response.getData().toString());
    }
  }
}
