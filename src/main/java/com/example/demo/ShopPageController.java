package com.example.demo;

import com.example.demo.model.Category;
import com.example.demo.db.DateBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ShopPageController {
    ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Product, Category> shopPageCategory;

    @FXML
    private TableColumn<Product, String> shopPageDescription;

    @FXML
    private TableColumn<Product, Double> shopPagePrice;

    @FXML
    private TableColumn<Product, String> shopPageQuantity;

    @FXML
    private TableColumn<Product, Integer> shopPageId;

    @FXML
    private TableColumn<Product, String> shopPageNameProduct;

    @FXML
    private TableView<Product> shopPageTable;

    @FXML
    private Button shopPageButtonProduct;

    @FXML
    private Button shopPageButtonInHistory;

    //    @FXML
//    private Text shopPageText;
//


    @FXML
    private Label shopPageUserLogin1;


    @FXML
    void initialize() throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        fillTable();
        addButtonToTable();
        shopPageUserLogin1.setText(LoginPageController.login);
//        shopPageUserLogin1.setText("hello");
        shopPageButtonProduct.setOnAction(x -> {
            try {
                openPage("usersCartPage.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        shopPageButtonInHistory.setOnAction(x -> {
            try {
                openPage("historyOrder.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void openPage(String str) throws IOException {
        shopPageButtonProduct.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void fillTable() throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        products.clear();
        List<Product> productList = DateBaseConnection.getAllProducts();
        products.addAll(productList);
        shopPageId.setVisible(false);
        shopPageId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        shopPageNameProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        shopPageCategory.setCellValueFactory(new PropertyValueFactory<Product, Category>("category"));
        shopPageDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        shopPagePrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        shopPageQuantity.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));

        shopPageTable.setItems(products);
    }

    private void addButtonToTable() {
        TableColumn<Product, Void> colBtn = new TableColumn("Button Column");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    private final Button btn = new Button("Buy");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Product product = getTableView().getItems().get(getIndex());
                            try {
                                if (DateBaseConnection.checkQuantity(product.getId()) > 0) {
                                    DateBaseConnection.relationshipOfTable(product.getId(), LoginPageController.userId);
                                    DateBaseConnection.deleteQuantityFromDB(product.getId());
                                    fillTable();
                                } else {
                                    btn.setDisable(false);
                                    btn.setVisible(false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        });
                    }


                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }


                };

                return cell;
            }
        };


        colBtn.setCellFactory(cellFactory);

        shopPageTable.getColumns().add(colBtn);

    }

}

