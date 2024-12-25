package com.example.deneme;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class GirisKontrol {
    @FXML
    public TextField email;
    @FXML
    public PasswordField sifre;
    @FXML
    public Button girisButon;
    @FXML
    public ImageView slideShow;
    @FXML
    public Label uyari;

    public static List<Image> images;

    public int currentIndex = 0;

    @FXML
    public void GirisButon() throws IOException {
            String mail = email.getText();
            String password = sifre.getText();
            if (checkMailDatabase(mail) && checkPasswordDatabase(password)){
                anaEkran();
                ekranKapat();
            } else {

            }
    }

    public void anaEkran() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/deneme/scrollpane2139.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Romanza");
        stage.setScene(scene);
        stage.show();
    }

    public void ekranKapat(){
        Stage stage = (Stage) girisButon.getScene().getWindow();
        stage.close();
    }

    boolean checkMailDatabase(String mail){
        // MSSQL bağlantı bilgileri
        String url = "jdbc:sqlserver://LAPTOP-D5NN1BOC;databaseName=OnlineKitapSatisi;encrypt=true;trustServerCertificate=true";
        String user = "vtys1";
        String password = "12345";

        Connection connection = null;

        try {
            // Bağlantıyı oluştur
            connection = DriverManager.getConnection(url, user, password);

            // SQL sorgusu çalıştırma
            String query1 = "SELECT * FROM dbo.Users";
            Statement statement1 = connection.createStatement();
            ResultSet Email = statement1.executeQuery(query1);
            // Verileri yazdır
            while (Email.next()) {
                if (mail.equals(Email.getString("Email"))){
                    return true;
                }

            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    boolean checkPasswordDatabase(String pass){
        // MSSQL bağlantı bilgileri
        String url = "jdbc:sqlserver://LAPTOP-D5NN1BOC;databaseName=OnlineKitapSatisi;encrypt=true;trustServerCertificate=true";
        String user = "vtys1";
        String password = "12345";

        Connection connection = null;

        try {
            // Bağlantıyı oluştur
            connection = DriverManager.getConnection(url, user, password);

            // SQL sorgusu çalıştırma
            String query1 = "SELECT * FROM dbo.Users";
            Statement statement1 = connection.createStatement();
            ResultSet Email = statement1.executeQuery(query1);
            // Verileri yazdır
            while (Email.next()) {
                if (pass.equals(Email.getString("Password"))){
                    return true;
                }

            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void initialize() {
        // Resim listesini oluştur
        images = new ArrayList<>();
        images.add(new Image("file:C:\\Users\\nailk\\Desktop\\deneme\\src\\main\\resources\\com\\example\\deneme\\slide1.jpg"));
        images.add(new Image("file:C:\\Users\\nailk\\Desktop\\deneme\\src\\main\\resources\\com\\example\\deneme\\slide2.jpg"));
        images.add(new Image("file:C:\\Users\\nailk\\Desktop\\deneme\\src\\main\\resources\\com\\example\\deneme\\slide3.jpg"));
        images.add(new Image("file:C:\\Users\\nailk\\Desktop\\deneme\\src\\main\\resources\\com\\example\\deneme\\slide4.jpg"));
        images.add(new Image("file:C:\\Users\\nailk\\Desktop\\deneme\\src\\main\\resources\\com\\example\\deneme\\slide5.jpg"));
        images.add(new Image("file:C:\\Users\\nailk\\Desktop\\deneme\\src\\main\\resources\\com\\example\\deneme\\slide6.jpg"));
        images.add(new Image("file:C:\\Users\\nailk\\Desktop\\deneme\\src\\main\\resources\\com\\example\\deneme\\slide7.jpg"));

        // İlk resmi yükle
        if (!images.isEmpty()) {
            slideShow.setImage(images.get(0));
        }

        // Slideshow başlat
        startSlideshow();
    }

    private void startSlideshow() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> {
                    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), slideShow);
                    fadeOut.setFromValue(1.0);
                    fadeOut.setToValue(0.0);

                    fadeOut.setOnFinished(e -> {
                        currentIndex = (currentIndex + 1) % images.size();
                        slideShow.setImage(images.get(currentIndex));

                        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), slideShow);
                        fadeIn.setFromValue(0.0);
                        fadeIn.setToValue(1.0);
                        fadeIn.play();
                    });

                    fadeOut.play();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}

