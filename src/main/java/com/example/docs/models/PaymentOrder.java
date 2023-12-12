package com.example.docs.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PaymentOrder extends Document {
    protected String employee;

    public PaymentOrder(String number, String user, Double sum, String employee) {
        super(number, user, sum);
        this.employee = employee;
    }

    public PaymentOrder(String number, String user, Double sum, String employee, String date){
        super(number, user, sum);
        this.sum = sum;
        this.currentDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Платёжка от " + getCurrentDate() + " номер " + number;
    }

    @Override
    public String documentToFile() {
        return "Платёжка\n" +
                "Номер: " + number +
                "\nДата: " + getCurrentDate() +
                "\nПользователь: " + user +
                "\nСумма: " + sum +
                "\nСотрудник: " + employee;

    }

    public static PaymentOrder fromString(String data) {
        String[] lines = data.split("\n");

        try {
            String number = getValue(lines[1]);
            String date = getValue(lines[2]);
            String user = getValue(lines[3]);
            Double sum = Double.parseDouble(getValue(lines[4]));
            String employee = getValue(lines[5]);

            return new PaymentOrder(number, user, sum, employee, date);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }
}
