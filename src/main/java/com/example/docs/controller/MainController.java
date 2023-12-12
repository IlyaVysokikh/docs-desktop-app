package com.example.docs.controller;

import com.example.docs.models.Document;
import com.example.docs.models.Invoice;
import com.example.docs.models.PaymentOrder;
import com.example.docs.models.PaymentRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.*;

import java.util.Optional;

public class MainController {

    @FXML
    private ListView<Document> listView;

    private final ObservableList<Document> documentList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        listView.setItems(documentList);
    }

    private Parent loadFXML(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    protected void createInvoice() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docs/invoice-dialog.fxml"));
        Parent root = loadFXML(loader);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Создание Накладной");
        dialog.setHeaderText(null);
        dialog.setResizable(false);

        dialog.getDialogPane().setContent(root);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


        InvoiceController invoiceController = loader.getController();

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String user = invoiceController.getUserTextField();
            String number = invoiceController.getNumberTextField();
            Double sum = Double.parseDouble(invoiceController.getAmountField());
            String currency = invoiceController.getCurrencyField();
            Double rate = Double.parseDouble(invoiceController.getExchangeRateField());
            String product = invoiceController.getProductTextField();
            Double countProduct = Double.parseDouble(invoiceController.getQuantityField());

            Document invoice = new Invoice(number, user, sum, currency, rate, product, countProduct);
            documentList.add(invoice);
        }

    }

    @FXML
    protected void createPaymentOrder() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docs/payment-order-dialog.fxml"));
        Parent root = loadFXML(loader);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Создание Платёжки");
        dialog.setHeaderText(null);
        dialog.setResizable(false);

        dialog.getDialogPane().setContent(root);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


        PaymentOrderController paymentOrderController = loader.getController();

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String user = paymentOrderController.getUserTextField();
            String number = paymentOrderController.getNumberTextField();
            Double sum = Double.parseDouble(paymentOrderController.getAmountField());
            String employee = paymentOrderController.getEmployeeField();

            Document paymentOrder = new PaymentOrder(number, user, sum, employee);
            documentList.add(paymentOrder);
        }

    }

    @FXML
    protected void createPaymentRequest() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docs/payment-request-dialog.fxml"));
        Parent root = loadFXML(loader);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Создание Заявки на оплату");
        dialog.setHeaderText(null);
        dialog.setResizable(false);

        dialog.getDialogPane().setContent(root);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


        PaymentRequestController paymentRequestController = loader.getController();

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String user = paymentRequestController.getUserTextField();
            String number = paymentRequestController.getNumberTextField();
            Double sum = Double.parseDouble(paymentRequestController.getAmountField());
            String counterparty = paymentRequestController.getCounterpartyField();
            String currency = paymentRequestController.getCurrencyField();
            Double rate = Double.parseDouble(paymentRequestController.getRateField());
            Double commission = Double.parseDouble(paymentRequestController.getCommissionField());

            Document paymentRequest = new PaymentRequest(number, user, sum, counterparty, currency, rate, commission);
            documentList.add(paymentRequest);

        }
    }

    @FXML
    private void saveDocument() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить документ");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)",
                "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            Document selectedDocument = listView.getSelectionModel().getSelectedItem();
            if (selectedDocument != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(selectedDocument.documentToFile());
                    System.out.println("Документ сохранен в файл: " + file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void loadDocument() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбрать файл для загрузки");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)",
                "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                StringBuilder content = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                Document loadedDocument = parseDocument(content.toString());

                if (loadedDocument != null) {
                    documentList.add(loadedDocument);
                    System.out.println("Документ загружен из файла: " + file.getAbsolutePath());
                } else {
                    System.out.println("Ошибка при загрузке документа из файла.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Document parseDocument(String data) {
        String[] lines = data.split("\n");

        try {
            String docType = lines[0].trim();
            switch (docType) {
                case "Накладная":
                    return Invoice.fromString(data);
                case "Платёжка":
                    return PaymentOrder.fromString(data);
                case "Заявка на оплату":
                    return PaymentRequest.fromString(data);
                default:
                    System.out.println("Неизвестный тип документа: " + docType);
                    return null;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }


    @FXML
    private void showDocumentProperties() {
        Document selectedDocument = listView.getSelectionModel().getSelectedItem();
        if(selectedDocument != null) {
            Alert documentProperties = new Alert(Alert.AlertType.INFORMATION);
            documentProperties.setTitle(selectedDocument.toString());
            documentProperties.setHeaderText(null);
            documentProperties.setContentText(selectedDocument.documentToFile());

            documentProperties.showAndWait();
        } else {
            showAlert("Вы не выбрали документ для просмотра");
        }
    }

    @FXML
    private void deleteDocument() {
        Document selectedDocument = listView.getSelectionModel().getSelectedItem();
        if(selectedDocument != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Удаление документа");
            alert.setHeaderText(null);
            alert.setContentText("Вы уверены, что хотите удалить документ " + selectedDocument + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                documentList.remove(selectedDocument);
                listView.refresh(); // Обновляем отображение в listView
                showAlert("Документ успешно удалён из списка");
            }
        } else {
            showAlert("Вы не выбрали документ для удаления из списка");
        }
    }

    private void showAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Уведомление");
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    private void close(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Закрытие приложения");
        alert.setHeaderText(null);
        alert.setContentText("Вы уверены, что хотите закрыть приложение?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) listView.getScene().getWindow();
            stage.close();
        }
    }
}