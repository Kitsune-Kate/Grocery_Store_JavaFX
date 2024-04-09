package com.example.demo;

import com.example.demo.db.DateBaseConnection;
import com.example.demo.model.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController {

    ObservableList<Category> genreList = FXCollections.observableArrayList(Category.values());

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button adminPageAddProductButton;

    @FXML
    private ComboBox<Category> adminPageCategoryProduct;

    @FXML
    private TextField adminPageDescriptionProduct;

    @FXML
    private TextField adminPageNameProduct;

    @FXML
    private TextField adminPagePriceProduct;

    @FXML
    private TextField adminPageQuantityProduct;
    @FXML
    private Label adminPageError;

    @FXML
    void initialize() {
        adminPageCategoryProduct.setItems(genreList);
        adminPageAddProductButton.setOnAction(x -> {
            try {
                if (DateBaseConnection.checkNameOfProduct(adminPageNameProduct.getText())) {
                    try {
                        DateBaseConnection.addProductsInDb(new Product
                                (adminPageNameProduct.getText(), adminPageCategoryProduct.getValue(),
                                        Double.valueOf(adminPagePriceProduct.getText()),Integer.parseInt(adminPageQuantityProduct.getText()), String.valueOf(adminPageDescriptionProduct.getText())));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    adminPageError.setText("Продукт уже существует");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        });
    }

}


