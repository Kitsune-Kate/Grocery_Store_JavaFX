package com.example.demo;

import com.example.demo.db.DateBaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPageController {
    public static int userId = 0;
    public static String login;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginPageLoginButton;

    @FXML
    private TextField loginPageLoginField;

    @FXML
    private PasswordField loginPagePasswordInput;

    @FXML
    private Button loginPageRegistrationButton;

    @FXML
    private Text loginPageLoginError;

    @FXML
    void initialize() {
        loginPageLoginButton.setOnAction(x -> {
            System.out.println("button LoginPageLoginButton");
        });
        loginPageRegistrationButton.setOnAction(x -> {
            try {
                openPage("registrationPage.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        loginPageLoginButton.setOnAction(x -> {
            try {
                if (DateBaseConnection.getAuthorizationUser(loginPageLoginField.getText(), loginPagePasswordInput.getText())) {
                    if (Objects.equals(DateBaseConnection.getRole(loginPageLoginField.getText(), loginPagePasswordInput.getText()), "admin")) {
                        openPage("adminPage.fxml");
                    } else {
                        userId = DateBaseConnection.getId(loginPageLoginField.getText(), loginPagePasswordInput.getText());
                        login = loginPageLoginField.getText();
                        openPage("shopMain.fxml");
                    }
                } else {
                    loginPageLoginError.setText("Пользователь не найден");
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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void openPage(String str) throws IOException {
        loginPageRegistrationButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
