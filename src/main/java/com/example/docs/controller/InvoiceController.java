package com.example.docs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class InvoiceController extends AbstractController {


    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField currencyField;

    @FXML
    private TextField rateField;

    @FXML
    private TextField productField;

    @FXML
    private TextField quantityField;


    public String getProductTextField() {
        return productField.getText();
    }


    public String getCurrencyField() {
        return currencyField.getText();
    }

    public String getExchangeRateField() {
        return rateField.getText();
    }

    public String getQuantityField() {
        return quantityField.getText();
    }
}
