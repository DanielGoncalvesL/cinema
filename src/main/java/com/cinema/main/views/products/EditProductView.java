package com.cinema.main.views.products;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import com.cinema.application.dtos.products.EditProductInfosDTO;
import com.cinema.application.dtos.products.ProductInfosDTO;
import com.cinema.application.helpers.Response;
import com.cinema.main.factories.products.UpdateProductFactory;
import com.cinema.main.views.helpers.AlertError;
import com.cinema.main.views.helpers.AlertSuccess;
import com.cinema.main.views.helpers.CurrencyField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * This class represents the view for editing a product.
 * It implements the Initializable interface to initialize the view.
 */
public class EditProductView implements Initializable {
  @FXML
  private TextField name;

  @FXML
  private CurrencyField price;

  @FXML
  private TextField quantity;

  private ProductInfosDTO product;

  /**
    * Initializes the EditProductView.
    * This method is called after the FXML file has been loaded and the controller object has been created.
    * It is used to perform any necessary initialization tasks.
    *
    * @param location   The location used to resolve relative paths for the root object, or null if the location is not known.
    * @param resources  The resources used to localize the root object, or null if the root object was not localized.
    */
  public void initialize(URL location, ResourceBundle resources) {
    if (price != null) {
      price.setCurrencyFormat(Locale.US);
    }

    quantity.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        quantity.setText("0");
      }
    });

    product = ProductModel.getInstance().getProduct();

    name.setText(product.getName());
    price.setAmount(product.getPrice());
    quantity.setText(String.valueOf(product.getQuantity()));
  }

  /**
   * Handles the action event when the user clicks on the "Edit Product" button.
   * Retrieves the updated product information from the input fields and sends it to the server for updating.
   * Displays a success message if the product is successfully edited, or an error message if an error occurs.
   *
   * @param event The action event triggered by clicking the "Edit Product" button.
   */
  @FXML
  void editProduct(ActionEvent event) {
    EditProductInfosDTO updateProductDTO = new EditProductInfosDTO(product.getID(), name.getText(), price.getAmount(),
        Integer.parseInt(quantity.getText()), product.getInventoryID());

    Response<?> response = UpdateProductFactory.make().handle(updateProductDTO);

    if (response.getStatusCode() == 204) {
      new AlertSuccess("Produto editado com sucesso!");
    } else {
      new AlertError(response.getData().toString());
    }
  }
}