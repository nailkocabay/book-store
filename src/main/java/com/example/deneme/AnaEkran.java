package com.example.deneme;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;

public class AnaEkran {
    public void showBookDetails(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String bookTitle = clickedButton.getText();

        // Kitap başlığına göre detay gösterme
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kitap Detayları");
        alert.setHeaderText(null);
        alert.setContentText("Seçilen Kitap: " + bookTitle);

        alert.showAndWait();
    }
}
