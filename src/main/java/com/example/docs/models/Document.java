package com.example.docs.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Document implements Serializable {

    protected String number;

    protected LocalDate currentDate;

    protected String user;

    protected Double sum;

//    protected  LocalDateTime timestamp;


    public Document(String number, String user, Double sum) {
        this.number = number;
        this.user = user;
        this.sum = sum;
        currentDate = LocalDate.now();
//        this.timestamp = LocalDateTime.now();
    }
    protected String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return currentDate.format(formatter);
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return currentDate;
    }

    public String getUser() {
        return user;
    }

    public Double getSum() {
        return sum;
    }

    public abstract String documentToFile();

    protected static String getValue(String line) {
        return line.substring(line.indexOf(":") + 1).trim();
    }


}
