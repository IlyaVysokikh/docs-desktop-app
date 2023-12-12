package com.example.docs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PaymentRequestController extends  AbstractController{

    @FXML
    private TextField counterpartyField;

    @FXML
    private TextField currencyField;

    @FXML
    private  TextField rateField;

    @FXML
    private  TextField commissionField;

    public String getCounterpartyField() { return counterpartyField.getText(); }

    public String getCurrencyField() { return currencyField.getText(); }

    public String getRateField() { return rateField.getText(); }

    public String getCommissionField() { return commissionField.getText(); }
}
