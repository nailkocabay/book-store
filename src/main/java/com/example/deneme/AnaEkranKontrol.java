package com.example.deneme;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


import java.sql.Connection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnaEkranKontrol {

    @FXML
    public Button kullanici;
    @FXML
    public Button sepet;
    @FXML
    public Button exit;
    @FXML
    public Button siparisler;
    @FXML
    public TextField arama;

    // Sepete eklenen kitaplar listesi
    private List<String> Sepet = new ArrayList<>();
    @FXML
    private VBox bookContainer; // FXML'deki fx:id ile eşleşmelidir.

    public void initialize() {
        // JavaFX initialize metodu. FXML yüklendikten sonra otomatik çağrılır.
        loadBooksFromDatabase();
    }

    private void loadBooksFromDatabase() {
        String url = "jdbc:sqlserver://LAPTOP-D5NN1BOC;databaseName=OnlineKitapSatisi;encrypt=true;trustServerCertificate=true";
        String user = "vtys1";
        String password = "12345";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = " SELECT Books.Title, BooksImages.ImageURL FROM Books JOIN BooksImages ON Books.BookID = BooksImages.BookID";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            VBox contentVBox = new VBox(20); // İçerik için VBox, kitaplar burada saklanacak
            contentVBox.setStyle("-fx-padding: 10; -fx-alignment: top-center;");

            HBox currentRow = new HBox(10); // İlk satır için bir HBox oluştur
            int columnCount = 0;
            final int maxColumns = 5; // Bir satırdaki maksimum kitap sayısı

            while (resultSet.next()) {
                String title = resultSet.getString("Title");
                String imageUrl = resultSet.getString("ImageURL");

                // Kitap için bir VBox oluştur
                VBox bookBox = new VBox(5);
                bookBox.setStyle("-fx-alignment: center;"); // İçeriği ortala

                // Kitap görselini oluştur ve ekle
                ImageView imageView = new ImageView(new Image(imageUrl)); // URL'den resim yükleme
                imageView.setFitHeight(225);
                imageView.setFitWidth(150);

                // "Sepete Ekle" butonunu oluştur ve ekle
                Button addToCartButton = new Button("Sepete Ekle");
                addToCartButton.setOnAction(event -> sepeteEkle(title));

                // Kitap detayları için buton
                Button bookButton = new Button();
                bookButton.setGraphic(imageView); // Görseli butonun içine ekle
                bookButton.setOnAction(event -> kitapDetayGoster(title));
                bookButton.setStyle("-fx-background-color: transparent; -fx-padding: 10;"); // Görünmez buton

                // Görseli ve butonu VBox'a ekle
                bookBox.getChildren().addAll(bookButton, addToCartButton);

                // Mevcut satıra kitap kutusunu ekle
                currentRow.getChildren().add(bookBox);
                columnCount++;

                // Maksimum kitap sayısına ulaşıldığında yeni bir satır oluştur
                if (columnCount == maxColumns) {
                    contentVBox.getChildren().add(currentRow); // Mevcut satırı içeriğe ekle
                    currentRow = new HBox(10); // Yeni satır başlat
                    columnCount = 0;
                }
            }

            // Eğer döngüden sonra eklenmemiş kitaplar varsa son satırı ekle
            if (!currentRow.getChildren().isEmpty()) {
                contentVBox.getChildren().add(currentRow);
            }

            // ScrollPane oluştur ve içeriği ayarla
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(contentVBox);
            scrollPane.setFitToWidth(true); // Genişlik içeriğe uyar
            scrollPane.setPannable(true); // Kaydırılabilir

            // ScrollPane'i ana VBox'a ekle
            bookContainer.getChildren().add(scrollPane);

        } catch (Exception e) {
            e.printStackTrace(); // Hata durumunda loglama
        }
    }







    // Sepete ekleme işlemi
    private void sepeteEkle(String kitapAdi) {
        Sepet.add(kitapAdi);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sepete Ekleme");
        alert.setHeaderText(null);
        alert.setContentText(kitapAdi + " sepete eklendi!");
        alert.showAndWait();
    }

    // Kitap detaylarını gösterme işlemi
    private void kitapDetayGoster(String kitapAdi) {
        String url = "jdbc:sqlserver://LAPTOP-D5NN1BOC;databaseName=OnlineKitapSatisi;encrypt=true;trustServerCertificate=true";
        String user = "vtys1";
        String password = "12345";

            try (Connection connection1 = DriverManager.getConnection(url, user, password)) {
                String query = "SELECT Books.BookID, Title, Price, ISBN, Authors.Name AS AuthorName, Publishers.Name AS PublisherName " +
                        "FROM dbo.Books " +
                        "JOIN dbo.Authors ON dbo.Books.AuthorID = dbo.Authors.AuthorID " +
                        "JOIN dbo.Publishers ON dbo.Books.PublisherID = dbo.Publishers.PublisherID " +
                        "ORDER BY Books.BookID ASC";
                PreparedStatement preparedStatement = connection1.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    String title = resultSet.getString("Title");
                    double price = resultSet.getDouble("Price");
                    String isbn = resultSet.getString("ISBN");
                    String authorName = resultSet.getString("AuthorName");
                    String publisherName = resultSet.getString("PublisherName");

                    // Bu bilgileri işleyebilirsiniz
                    if (kitapAdi.equals(title)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Kitap Detayları");
                        alert.setHeaderText(null);
                        alert.setContentText("Kitap Adı: " + title + "\n" +
                                "Yazar: " + authorName + "\n" +
                                "Yayınevi: " + publisherName + "\n" +
                                "ISBN: " + isbn + "\n" +
                                "Fiyat: " + price);
                        alert.showAndWait();
                        break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }


    public void KitapEkleButon() throws IOException{
        System.out.println("Kitap Ekleme Ekranı");
        ekranOlustur("KitapEkleme.fxml");
        ekranKapat();
    }

    public void YazarEkleButon() throws IOException{
        String yazarAdi = txtYazarAdi.getText();
        String yazarID = txtYazarID.getText();


        if (yazarAdi.isEmpty() || yazarID.isEmpty() ) {
            showAlert("Hata", "Tüm alanları doldurunuz.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Veritabanı bağlantısı
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-D5NN1BOC;databaseName=OnlineKitapSatisi;encrypt=true;trustServerCertificate=true", "vtys1", "12345");
            String sql = "INSERT INTO Authors (Name, AuthorID) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, yazarAdi);
            stmt.setString(2, yazarID);


            int rows = stmt.executeUpdate();
            if (rows > 0) {
                showAlert("Başarılı", "Yazar başarıyla eklendi.", Alert.AlertType.INFORMATION);
                clearFields();
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            showAlert("Hata", "Veritabanına bağlanırken bir hata oluştu: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    @FXML
    private TextField txtKitapID;
    @FXML
    private TextField txtYazarID;
    @FXML
    private TextField txtKitapAdi;
    @FXML
    private TextField txtYazarAdi;
    @FXML
    private TextField txtYayineviID;
    @FXML
    private TextField txtFiyat;
    @FXML
    private TextField txtStok;
    @FXML
    private Button KitapEkle;
    @FXML
    private Button YazarEkle;
    @FXML
    private TextField txtISBN;
    @FXML
    private TextField txtGenre;

    // Kitap ekleme işlemi
    @FXML
    public void KitapEklemeButon() {
        String kitapID = txtKitapID.getText();
        String kitapAdi = txtKitapAdi.getText();
        String yazarID = txtYazarID.getText();
        String yayineviID = txtYayineviID.getText();
        String genre = txtGenre.getText();
        String fiyat = txtFiyat.getText();
        String stok = txtStok.getText();
        String ISBN = txtISBN.getText();

        if (kitapAdi.isEmpty() || kitapID.isEmpty() || fiyat.isEmpty() || stok.isEmpty() || ISBN.isEmpty()) {
            showAlert("Hata", "Tüm alanları doldurunuz.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Veritabanı bağlantısı
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-D5NN1BOC;databaseName=OnlineKitapSatisi;encrypt=true;trustServerCertificate=true", "vtys1", "12345");
            String sql = "INSERT INTO Books (BookID,Title,AuthorID,PublisherID,GenreID, Price, StockQuantity, ISBN) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kitapID);
            stmt.setString(2, kitapAdi);
            stmt.setString(3, yazarID);
            stmt.setString(4, yayineviID);
            stmt.setString(5, genre);
            stmt.setDouble(6, Double.parseDouble(fiyat));
            stmt.setInt(7, Integer.parseInt(stok));
            stmt.setString(8, ISBN);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                showAlert("Başarılı", "Kitap başarıyla eklendi.", Alert.AlertType.INFORMATION);
                clearFields();
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            showAlert("Hata", "Veritabanına bağlanırken bir hata oluştu: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Bilgilendirme mesajı gösterme
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Alanları temizleme
    private void clearFields() {
        txtKitapAdi.clear();
        txtYazarAdi.clear();
        txtYayineviID.clear();
        txtFiyat.clear();
        txtStok.clear();
        txtISBN.clear();
        txtGenre.clear();
        txtResimID.clear();
        txtResimURL.clear();
        txtKitapID1.clear();
        txtYayineviID.clear();
        txtYazarID.clear();


    }

    @FXML
    private TextField txtResimID;
    @FXML
    private TextField txtResimURL;
    @FXML
    private Button ImageEkle;
    @FXML
    private TextField txtKitapID1;

    public void ImageEkleButon() throws IOException{
        String kitapID1 = txtKitapID1.getText();
        String resimID = txtResimID.getText();
        String resimURL = txtResimURL.getText();


        if (kitapID1.isEmpty() || resimID.isEmpty() || resimURL.isEmpty()) {
            showAlert("Hata", "Tüm alanları doldurunuz.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Veritabanı bağlantısı
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-D5NN1BOC;databaseName=OnlineKitapSatisi;encrypt=true;trustServerCertificate=true", "vtys1", "12345");
            String sql = "INSERT INTO BooksImages (BookID,ImageID,ImageURL) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kitapID1);
            stmt.setString(2, resimID);
            stmt.setString(3, resimURL);


            int rows = stmt.executeUpdate();
            if (rows > 0) {
                showAlert("Başarılı", "Resim başarıyla eklendi.", Alert.AlertType.INFORMATION);
                clearFields();
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            showAlert("Hata", "Veritabanına bağlanırken bir hata oluştu: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void SepetButon() throws IOException{
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sepete Ekleme");
        alert.setHeaderText(null);
        alert.setContentText("Sepet İçeriğiniz: " + Sepet.toString());
        alert.showAndWait();
        //ekranOlustur("Sepet.fxml");
        //ekranKapat();
    }

    public void ExitButon() throws IOException{
        ekranOlustur("GirisEkrani.fxml");
        ekranKapat();
    }






    public void ekranOlustur(String fxml_name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Giris.class.getResource(fxml_name));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        Stage stage = new Stage();
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    public void ekranKapat(){
        Stage stage = (Stage) kullanici.getScene().getWindow();
        stage.close();
    }

}