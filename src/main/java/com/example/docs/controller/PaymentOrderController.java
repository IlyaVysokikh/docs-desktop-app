package com.example.docs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PaymentOrderController extends AbstractController {

    @FXML
    private TextField employeeField;

    public String getEmployeeField() { return employeeField.getText(); }
}
