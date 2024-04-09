package com.example.demo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.example.demo.db.DateBaseConnection;
import com.example.demo.model.Category;
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

public class OrderHistoryController {
    ArrayList<Order> orders = DateBaseConnection.getOrdersForHistory(LoginPageController.userId);
    ObservableList<Order> ordersObservableList = FXCollections.observableArrayList(orders);
    List<Product> products = DateBaseConnection.getProductsFromCartInHistory(LoginPageController.userId, UsersCartPageController.orderNumberStr);
    ObservableList<Product> ordersObservableList2 = FXCollections.observableArrayList(products);

    @FXML
    private TableColumn<Order, Integer> orderHistoryPageId;

    @FXML
    private TableColumn<Order, Date> orderHistoryPageDate;

    @FXML
    private TableColumn<Order, String> orderHistoryPageOrderNumber;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Product, String> orderHistoryPageQuantity;

    @FXML
    private Text orderHistoryPageTitle;

    @FXML
    private TableView<Order> orderHistoryTableView;

    @FXML
    private TableView<Product> orderHistoryPageTableView2;

    @FXML
    private Button orderHistoryPageButtonBack;


    @FXML
    private TableColumn<Product, Category> orderHistoryPageCategory;

    @FXML
    private Label usersCartPageSum;

    @FXML
    private TableColumn<Product, Integer> orderHistoryPageId2;

    @FXML
    private TableColumn<Product, String> orderHistoryPageName;

    @FXML
    private TableColumn<Product, Double> orderHistoryPagePrice;

    @FXML
    private TableColumn<Product, String> orderHistoryPageDescription;

    @FXML
    private TableColumn<?, ?> orderHistoryPageFullPrice;

    public OrderHistoryController() throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        fillTable();
        orderHistoryPageId.setVisible(false);
//        switchingBetweenRows();
        addButtonToTable();
        orderHistoryPageButtonBack.setOnAction(x -> {
            try {
                openPage("usersCartPage.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }

    public void openPage(String str) throws IOException {
        orderHistoryPageButtonBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void fillTable2(List<Product> ordersList) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Date date = new Date();
        ordersObservableList2.clear();
//        List<Product> ordersList = DateBaseConnection.getProductsFromCartInHistory(LoginPageController.userId, UsersCartPageController.orderNumberStr);
        ordersObservableList2.addAll(ordersList);
        orderHistoryPageId2.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        orderHistoryPageName.setCellValueFactory(new PropertyValueFactory<Product, String>("Name"));
        orderHistoryPageCategory.setCellValueFactory(new PropertyValueFactory<Product, Category>("Category"));
        orderHistoryPagePrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("Price"));
        orderHistoryPageDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("Description"));
        orderHistoryPageQuantity.setCellValueFactory(new PropertyValueFactory<Product, String>("Quantity"));
        orderHistoryPageTableView2.setItems(ordersObservableList2);


    }

    public void fillTable() throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ordersObservableList.clear();
        List<Order> ordersList = DateBaseConnection.getOrdersForHistory(LoginPageController.userId);
        ordersObservableList.addAll(ordersList);
        orderHistoryPageId.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
        orderHistoryPageDate.setCellValueFactory(new PropertyValueFactory<Order, Date>("Date"));
        orderHistoryPageOrderNumber.setCellValueFactory(new PropertyValueFactory<Order, String>("orderNumber"));
        orderHistoryTableView.setItems(ordersObservableList);

    }

    private void addButtonToTable() {
        TableColumn<Order, Void> colBtn = new TableColumn("Button Column");
        System.out.println("hello");
        Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                final TableCell<Order, Void> cell = new TableCell<Order, Void>() {
                    private final Button btn = new Button("Buy");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Order order = getTableView().getItems().get(getIndex());
                            try {
                                List<Product> ordersList = DateBaseConnection.getProductsFromCartInHistory(LoginPageController.userId, order.getOrderNumber());
                                fillTable2(ordersList);
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
        orderHistoryTableView.getColumns().add(colBtn);

    }

//    private void switchingBetweenRows() {
//        Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
//            @Override
//            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
//                final TableCell<Order, Void> cell = new TableCell<Order, Void>() {
//                    {
//                        orderHistoryTableView.setRowFactory(tv ->
//
//                        {
//                            TableRow<Order> row = new TableRow<>();
//                            row.setOnMouseClicked(event -> {
//                                if (event.getClickCount() == 2 && (!row.isEmpty())) {
//                                    Order order = getTableView().getItems().get(getIndex());
//                                    Order rowData = row.getItem();
//                                    try {
//                                        List<Product> ordersList = DateBaseConnection.getProductsFromCartInHistory(LoginPageController.userId, order.getOrderNumber());
//                                        fillTable2(ordersList);
//
//                                    } catch (SQLException e) {
//                                        throw new RuntimeException(e);
//                                    } catch (ClassNotFoundException e) {
//                                        throw new RuntimeException(e);
//                                    } catch (InvocationTargetException e) {
//                                        throw new RuntimeException(e);
//                                    } catch (NoSuchMethodException e) {
//                                        throw new RuntimeException(e);
//                                    } catch (InstantiationException e) {
//                                        throw new RuntimeException(e);
//                                    } catch (IllegalAccessException e) {
//                                        throw new RuntimeException(e);
//                                    }
//                                }
//                            });
//                            return row;
//                        });
//                    }
//                };
//                return cell;
//            }
//
//
//        };
//    }
}
    






