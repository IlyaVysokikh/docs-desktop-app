package com.example.docs.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PaymentRequest extends Document {

    protected String counterparty;

    protected String currency;

    protected Double rate;

    protected Double commission;

    public PaymentRequest(String number, String user, Double sum, String counterparty,
                          String currency, Double rate, Double commission) {
        super(number, user, sum);
        this.counterparty = counterparty;
        this.currency = currency;
        this.rate = rate;
        this.commission = commission;
    }
    public PaymentRequest(String number, String user, Double sum, String counterparty,
                          String currency, Double rate, Double commission, String date) {
        super(number, user, sum);
        this.counterparty = counterparty;
        this.currency = currency;
        this.rate = rate;
        this.commission = commission;
        this.currentDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
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

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    @Override
    public String toString() {
        return "Заявка на оплату от " + getCurrentDate() + " номер " + number;
    }


    @Override
    public String documentToFile() {
        return "Заявка на оплату\n" +
                "Номер: " + number +
                "\nДата: " + getCurrentDate() +
                "\nПользователь: " + user +
                "\nСумма: " + sum +
                "\nКонтрагент: " + counterparty +
                "\nВалюта: " + currency +
                "\nКурс валюты: " + rate +
                "\nКомиссия: " + commission;

    }

    public static PaymentRequest fromString(String data) {
        String[] lines = data.split("\n");

        try {
            String number = getValue(lines[1]);
            String date = getValue(lines[2]);
            String user = getValue(lines[3]);
            Double sum = Double.parseDouble(getValue(lines[4]));
            String counterparty = getValue(lines[5]);
            String currency = getValue(lines[6]);
            Double rate = Double.parseDouble(getValue(lines[7]));
            Double commission = Double.parseDouble(getValue(lines[8]));

            return new PaymentRequest(number, user, sum, counterparty, currency, rate, commission, date);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }
}
