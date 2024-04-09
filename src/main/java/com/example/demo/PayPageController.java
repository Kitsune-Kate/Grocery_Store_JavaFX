package com.example.demo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.demo.db.DateBaseConnection;
import com.example.demo.model.Cards;
import com.example.demo.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PayPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField payPageFieldCartNumber;

    @FXML
    private TextField payPageFieldCVC;

    @FXML
    private Button payPageButtonPay;

    @FXML
    private Text payPageTextHeading;

    @FXML
    private Text payPageTextCVC;

    @FXML
    private Text payPageTextMail;

    @FXML
    private TextField payPageFieldMail;

    @FXML
    private Text payPageTextCartNumber;

    @FXML
    private Button payPageBack;

    @FXML
    void initialize() {
        payPageBack.setOnAction(x -> {
            try {
                openPage("usersCartPage.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        payPageButtonPay.setOnAction(x -> {
            try {
                DateBaseConnection.addCartNumber(LoginPageController.userId, new Cards(payPageFieldCartNumber.getText(), payPageFieldCVC.getText(), payPageFieldMail.getText()));
                openPage("historyOrder.fxml");

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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }

    public void openPage(String str) throws IOException {
        payPageBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}


