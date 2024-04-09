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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class UsersCartPageController {
    List<Product> products = DateBaseConnection.getAllUsersProducts(LoginPageController.userId);
    ObservableList<Product> productObservableList = FXCollections.observableArrayList(products);
    public static String orderNumberStr = "";


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button usersCartPageButtonSetAnOrder;

    @FXML
    private TableColumn<Product, Category> usersCartPageCategory;

    @FXML
    private TableColumn<Product, Integer> usersCartPageId;

    @FXML
    private TableColumn<Product, Double> usersCartPagePrice;

    @FXML
    private TableColumn<Product, String> usersCartPageProductName;

    @FXML
    private TableColumn<Product, String> usersCartPageQuantity;

    @FXML
    private TableView<Product> usersCartPageTableView;
    @FXML
    private Text usersCartPageTitle;

    @FXML
    private Label usersCartPageSum;

    @FXML
    private Label usersCartPageSumText;

    @FXML
    private Button usersCartPageButtonBack;

    public UsersCartPageController() throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        fillTable();
        deleteQuantityProductFromTable();
        addOneProductFromTable();
        deleteOneProductFromTable();
        usersCartPageSum.setText(sumInCart());
        usersCartPageButtonSetAnOrder.setOnAction(x -> {
            try {
                String str = "";
                for (int i = 0; i < 10; i++) {
                    String num = (int) (Math.random() * 10) + "";
                    str = str + num;
                }
                String orderNumber = str;
                orderNumberStr = orderNumber;
                DateBaseConnection.createOrderInDb(LoginPageController.userId, orderNumber, new Date());
                int orderId = DateBaseConnection.getOrderId(LoginPageController.userId, orderNumber);
                DateBaseConnection.fillOrder(LoginPageController.userId, orderId);
                DateBaseConnection.payCart(LoginPageController.userId);
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


        usersCartPageButtonBack.setOnAction(x -> {
            try {
                openPage("shopMain.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void openPage(String str) throws IOException {
        usersCartPageButtonSetAnOrder.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void fillTable() throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        productObservableList.clear();
        List<Product> productList = DateBaseConnection.getAllUsersProducts(LoginPageController.userId);
        productObservableList.addAll(productList);
        if (productList.isEmpty()) {
            usersCartPageButtonSetAnOrder.setVisible(false);
        }
        usersCartPageId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        usersCartPageProductName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        usersCartPageCategory.setCellValueFactory(new PropertyValueFactory<Product, Category>("category"));
        usersCartPagePrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        usersCartPageQuantity.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));
        usersCartPageTableView.setItems(productObservableList);



    }

    public String sumInCart() {
        String sum = String.valueOf(products.stream().mapToDouble(x -> x.getPrice() * (double) (x.getQuantity())).sum());
        String[] rub = sum.split("\\.");
        String pennies = "";
        if (rub.length < 2) {
            pennies = rub[1].substring(0, 1);
        }
//       else{
//            pennies = rub[1].substring(0, 2);
//        }
        String rubs = rub[0] + "." + pennies;
        return rubs;
    }

    private void deleteQuantityProductFromTable() {
        TableColumn<Product, Void> colBtn = new TableColumn("Button Column");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Product product = getTableView().getItems().get(getIndex());
                            try {
                                    DateBaseConnection.deleteProductFromCart(LoginPageController.userId,product.getId());
                                    fillTable();
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

        usersCartPageTableView.getColumns().add(colBtn);

    }

    private void deleteOneProductFromTable() {
        TableColumn<Product, Void> colBtn = new TableColumn("Button Column");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    private final Button btn = new Button("-");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Product product = getTableView().getItems().get(getIndex());
                            try {
                                DateBaseConnection.deleteQuantityUserProductFromDB(LoginPageController.userId,product.getId());
                                DateBaseConnection.addQuantityProductFromDB(product.getId());
                                fillTable();
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

        usersCartPageTableView.getColumns().add(colBtn);
    }

    private void addOneProductFromTable() {
        TableColumn<Product, Void> colBtn = new TableColumn("Button Column");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    private final Button btn = new Button("+");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Product product = getTableView().getItems().get(getIndex());
                            try {
                                DateBaseConnection.addQuantityUserProductFromDB(LoginPageController.userId,product.getId());
                                DateBaseConnection.deleteQuantityProductFromDB(product.getId());
                                fillTable();
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

        usersCartPageTableView.getColumns().add(colBtn);

    }

}





