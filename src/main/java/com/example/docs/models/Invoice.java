package com.example.docs.models;

import java.time.LocalDate;

public class Invoice extends Document {

    private String currency;
    private Double rate;
    private String product;
    private Double countProduct;

    public Invoice(String number, String user, Double sum, String currency,
                   Double rate, String product, Double countProduct) {
        super(number, user, sum);
        this.currency = currency;
        this.rate = rate;
        this.product = product;
        this.countProduct = countProduct;
    }

    public Invoice(String number, String user, Double sum, String currency,
                   Double rate, String product, Double countProduct, LocalDate invoiceDate) {
        super(number, user, sum);
        this.currency = currency;
        this.rate = rate;
        this.product = product;
        this.countProduct = countProduct;
        this.currentDate = invoiceDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getCountProduct() {
        return countProduct;
    }

    public void setCountProduct(Double countProduct) {
        this.countProduct = countProduct;
    }

    @Override
    public String toString() {
        return "Накладная от " + getCurrentDate() + " номер " + number;
    }

    @Override
    public String documentToFile() {
        return "Накладная\n" +
                "Номер: " + number +
                "\nДата: " + getCurrentDate() +
                "\nПользователь: " + user +
                "\nСумма: " + sum +
                "\nВалюта: " + currency +
                "\nКурс валюты: " + rate +
                "\nТовар: " + product +
                "\nКоличество товара: " + countProduct;
    }

    public static Invoice fromString(String data) {
        String[] lines = data.split("\n");

        try {
            String number = getValue(lines[1]);
            String date = getValue(lines[2]);
            String user = getValue(lines[3]);
            double sum = Double.parseDouble(getValue(lines[4]));
            String currency = getValue(lines[5]);
            double rate = Double.parseDouble(getValue(lines[6]));
            String product = getValue(lines[7]);
            double countProduct = Double.parseDouble(getValue(lines[8]));

            return new Invoice(number, user, sum, currency, rate, product, countProduct);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

}
