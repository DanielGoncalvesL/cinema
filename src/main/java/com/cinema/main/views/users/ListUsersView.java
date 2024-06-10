package com.cinema.main.views.users;

import java.util.List;

import com.cinema.application.dtos.users.PersonDTO;
import com.cinema.application.helpers.Response;
import com.cinema.main.factories.users.ListPersonsFactory;
import com.cinema.main.views.helpers.ButtonTableCell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListUsersView {

  @FXML
  private TableColumn<PersonDTO, String> CPF;

  @FXML
  private TableColumn<PersonDTO, Void> action;

  @FXML
  private TableColumn<PersonDTO, String> name;

  @FXML
  private TableColumn<PersonDTO, String> type;

  @FXML
  private TableView<PersonDTO> users;

  @FXML
  public void initialize() {
    Response<?> response = ListPersonsFactory.make().handle(null);

    Object data = response.getData();

    if (data instanceof List) {
      ObservableList<PersonDTO> persons = FXCollections.observableArrayList();

      for (Object person : (List<?>) data) {
        if (person instanceof PersonDTO) {
          PersonDTO personDTO = (PersonDTO) person;

          persons.add(personDTO);
        }
      }

      this.users.setItems(persons);
    }

    this.name.setCellValueFactory(new PropertyValueFactory<>("name"));
    this.name.setStyle("-fx-alignment: CENTER;");

    this.CPF.setCellValueFactory(new PropertyValueFactory<>("CPF"));
    this.CPF.setStyle("-fx-alignment: CENTER;");

    this.type.setCellValueFactory(new PropertyValueFactory<>("role"));
    this.type.setStyle("-fx-alignment: CENTER;");

    this.action.setCellFactory(column -> new ButtonTableCell<>("Excluir", this::deletePerson));
  }

  private void deletePerson(PersonDTO person) {
    System.out.println("Deleting person: " + person.getName());
  }
}
