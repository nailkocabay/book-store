package com.example.deneme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        // MSSQL bağlantı bilgileri
        String url = "jdbc:sqlserver://LAPTOP-D5NN1BOC;databaseName=OnlineKitapSatisi;encrypt=true;trustServerCertificate=true";
        String user = "vtys1";
        String password = "12345";

        Connection connection = null;
        try {
            // Bağlantıyı oluştur
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Veritabanına başarılı bir şekilde bağlandı!");




            // SQL sorgusu çalıştırma
            String query1 = "SELECT * FROM dbo.Addresses";
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(query1);

            // Verileri yazdır
            while (resultSet1.next()) {
                System.out.println("AddressID: " + resultSet1.getInt("AddressID"));
                System.out.println("UserID: " + resultSet1.getInt("UserID"));
                System.out.println("Street: " + resultSet1.getString("Street"));
                System.out.println("City: " + resultSet1.getString("City"));
                System.out.println("State: " + resultSet1.getString("State"));
                System.out.println("PostalCode: " + resultSet1.getString("PostalCode"));
                System.out.println("Country: " + resultSet1.getString("Country"));
            }

            // SQL sorgusu çalıştırma
            String query2 = "SELECT * FROM dbo.Authors";
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(query2);

            // Verileri yazdır
            while (resultSet2.next()) {
                System.out.println("AuthorID: " + resultSet2.getInt("AuthorID"));
                System.out.println("Name: " + resultSet2.getString("Name"));
                System.out.println("Biography: " + resultSet2.getString("Biography"));
            }

            // SQL sorgusu çalıştırma
            String query3 = "SELECT * FROM dbo.Books";
            Statement statement3 = connection.createStatement();
            ResultSet resultSet3 = statement3.executeQuery(query3);
            // Verileri yazdır
            while (resultSet3.next()) {
                System.out.println("BookID: " + resultSet3.getInt("BookID"));
                System.out.println("Title: " + resultSet3.getString("Title"));
                System.out.println("AuthorID: " + resultSet3.getInt("AuthorID"));
                System.out.println("PublisherID: " + resultSet3.getInt("PublisherID"));
                System.out.println("GenreID: " + resultSet3.getInt("GenreID"));
                System.out.println("Price: " + resultSet3.getDouble("Price"));
                System.out.println("StockQuantity: " + resultSet3.getInt("StockQuantity"));
                System.out.println("ISBN: " + resultSet3.getString("ISBN"));
            }

            // SQL sorgusu çalıştırma
            String query4 = "SELECT * FROM dbo.BooksImages";
            Statement statement4 = connection.createStatement();
            ResultSet resultSet4 = statement4.executeQuery(query4);

            // Verileri yazdır
            while (resultSet4.next()) {
                System.out.println("ImageID: " + resultSet4.getInt("ImageID"));
                System.out.println("BookID: " + resultSet4.getInt("BookID"));
                System.out.println("ImageURL: " + resultSet4.getString("ImageURL"));
            }

            // SQL sorgusu çalıştırma
            String query5 = "SELECT * FROM dbo.Genres";
            Statement statement5 = connection.createStatement();
            ResultSet resultSet5 = statement5.executeQuery(query5);
            // Verileri yazdır
            while (resultSet5.next()) {
                System.out.println("GenreID: " + resultSet5.getInt("GenreID"));
                System.out.println("Name: " + resultSet5.getString("Name"));
            }

            // SQL sorgusu çalıştırma
            String query6 = "SELECT * FROM dbo.OrderDetails";
            Statement statement6 = connection.createStatement();
            ResultSet resultSet6 = statement6.executeQuery(query6);

            // Verileri yazdır
            while (resultSet6.next()) {
                System.out.println("OrderDetailID: " + resultSet6.getInt("OrderDetailID"));
                System.out.println("OrderID: " + resultSet6.getInt("OrderID"));
                System.out.println("BookID: " + resultSet6.getInt("BookID"));
                System.out.println("Quantity: " + resultSet6.getInt("Quantity"));
                System.out.println("Price: " + resultSet6.getDouble("Price"));
            }

            // SQL sorgusu çalıştırma
            String query7 = "SELECT * FROM dbo.Orders";
            Statement statement7 = connection.createStatement();
            ResultSet resultSet7 = statement7.executeQuery(query7);

            // Verileri yazdır
            while (resultSet7.next()) {
                System.out.println("OrderID: " + resultSet7.getInt("OrderID"));
                System.out.println("UserID: " + resultSet7.getInt("UserID"));
                System.out.println("OrderDate: " + resultSet7.getDate("OrderDate"));
                System.out.println("TotalAmount: " + resultSet7.getDouble("TotalAmount"));
                System.out.println("Status: " + resultSet7.getString("Status"));
            }

            // SQL sorgusu çalıştırma
            String query8 = "SELECT * FROM dbo.Payments";
            Statement statement8 = connection.createStatement();
            ResultSet resultSet8 = statement8.executeQuery(query8);

            // Verileri yazdır
            while (resultSet8.next()) {
                System.out.println("PaymentID: " + resultSet8.getInt("PaymentID"));
                System.out.println("OrderID: " + resultSet8.getInt("OrderID"));
                System.out.println("PaymentDate: " + resultSet8.getDate("PaymentDate"));
                System.out.println("PaymentMethod: " + resultSet8.getString("PaymentMethod"));
                System.out.println("Amount: " + resultSet8.getDouble("Amount"));
            }


            // SQL sorgusu çalıştırma
            String query9 = "SELECT * FROM dbo.Publishers";
            Statement statement9 = connection.createStatement();
            ResultSet resultSet9 = statement9.executeQuery(query9);

            // Verileri yazdır
            while (resultSet9.next()) {
                System.out.println("PublisherID: " + resultSet9.getInt("PublisherID"));
                System.out.println("Name: " + resultSet9.getString("Name"));
                System.out.println("AddressID: " + resultSet9.getInt("AddressID"));
                System.out.println("Phone: " + resultSet9.getString("Phone"));
            }


            // SQL sorgusu çalıştırma
            String query10 = "SELECT * FROM dbo.Reviews";
            Statement statement10 = connection.createStatement();
            ResultSet resultSet10 = statement10.executeQuery(query10);

            // Verileri yazdır
            while (resultSet10.next()) {
                System.out.println("ReviewID: " + resultSet10.getInt("ReviewID"));
                System.out.println("BookID: " + resultSet10.getInt("BookID"));
                System.out.println("UserID: " + resultSet10.getInt("UserID"));
                System.out.println("Rating: " + resultSet10.getInt("Rating"));
                System.out.println("Comment: " + resultSet10.getString("Comment"));
                System.out.println("ReviewDate: " + resultSet10.getDate("ReviewDate"));
            }


            // SQL sorgusu çalıştırma
            String query11 = "SELECT * FROM dbo.Users";
            Statement statement11 = connection.createStatement();
            ResultSet resultSet11 = statement11.executeQuery(query11);

            // Verileri yazdır
            while (resultSet11.next()) {
                System.out.println("UserID: " + resultSet11.getInt("UserID"));
                System.out.println("Name: " + resultSet11.getString("Name"));
                System.out.println("PasswordHash: " + resultSet11.getString("PasswordHash"));
                System.out.println("Email: " + resultSet11.getString("Email"));
                System.out.println("Phone: " + resultSet11.getString("Phone"));
                System.out.println("AddressID: " + resultSet11.getInt("AddressID"));
            }





        } catch (SQLException e) {
            System.out.println("Bağlantı hatası: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                System.out.println("Bağlantı kapatıldı.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
