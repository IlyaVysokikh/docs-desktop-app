package com.example.docs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public abstract class AbstractController {
    @FXML
    protected  TextField userField;

    @FXML
    protected TextField numberField;

    @FXML
    protected TextField amountField;


    public String getUserTextField() {
        return userField.getText();
    }

    public String getNumberTextField() {
        return numberField.getText();
    }

    public String getAmountField() { return amountField.getText(); }


}
