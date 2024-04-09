package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.db.DateBaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button registrationPageButtonAutorization;

    @FXML
    private Button registrationPageButtonRegistration;

    @FXML
    private TextField registrationPageEmail;

    @FXML
    private TextField registrationPageLogin;

    @FXML
    private TextField registrationPageName;

    @FXML
    private PasswordField registrationPagePassword;

    @FXML
    private PasswordField registrationPagePasswordConfirm;

    @FXML
    private Label registrationPageError;

    @FXML
    void initialize() {
        registrationPageButtonRegistration.setOnAction(x -> {
            if (registrationPagePassword.getText().equals(registrationPagePasswordConfirm.getText())) {
                try {
                 if(!DateBaseConnection.checkLoginUsers(registrationPageLogin.getText())) {
                     DateBaseConnection.addUserInDb(new User(registrationPageName.getText(), registrationPageLogin.getText(), registrationPagePassword.getText(), registrationPageEmail.getText()));
                     registrationPageError.setText("Регистрация прошла успешно");
                 }
                 else {
                     registrationPageError.setText("Пользователь уже существует");
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
            } else {
                registrationPageError.setText("Пароли не идентичны");
            }

        });
        registrationPageButtonAutorization.setOnAction(x->{
            try {
                openPage("hello-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public  void openPage(String str) throws IOException {
        registrationPageButtonAutorization.getScene().getWindow().hide();
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root=fxmlLoader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}





