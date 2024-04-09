package com.example.demo.db;


import com.example.demo.Order;
import com.example.demo.Product;
import com.example.demo.model.Cards;
import com.example.demo.model.Category;
import com.example.demo.model.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DateBaseConnection {
    public static Connection connectionDB() throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String url = "jdbc:mysql://localhost/shemashop";
        String username = "root";
        String password = "#decrypt{123.83.38.-93.20.-18.119.78.61.-4.-67}";
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        Connection conn = DriverManager.getConnection(url, username, password);

        return conn;
    }

    public static void addUserInDb(User user) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        if (!checkLoginUsers(user.getLogin())) {
            PreparedStatement preparedStatement = connectionDB().prepareStatement("insert into users (name,login,password,email,role) values(?,?,?,?,?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, "user");
            preparedStatement.executeUpdate();
        }


    }

    public static void addProductsInDb(Product product) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("insert into products (name,category,price,quantity,description) values(?,?,?,?,?)");
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, (String.valueOf(product.getCategory())));
        preparedStatement.setDouble(3, product.getPrice());
        preparedStatement.setInt(4, product.getQuantity());
        preparedStatement.setString(5, product.getDescription());
        preparedStatement.executeUpdate();


    }

    public static boolean checkLoginUsers(String loginNew) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("select * from users where login = ? ");
        preparedStatement.setString(1, loginNew);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return true;
        }

        return false;
    }

    public static boolean getAuthorizationUser(String login, String password) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("select * from users where login = ? and password = ? ");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return true;

        }
        return false;
    }

    public static String getRole(String login, String password) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("select * from users where login = ? and password = ? ");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String admin = resultSet.getString(6);
            return admin;

        }
        return null;
    }

    public static Integer getId(String login, String password) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("select * from users where login = ? and password = ? ");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            return id;

        }
        return null;
    }

    public static boolean checkNameOfProduct(String name) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("select * from products where name = ? ");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return false;
        }
        return true;
    }

    public static List<Product> getAllProducts() throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Product> products = new ArrayList<>();
        PreparedStatement preparedStatement = connectionDB().prepareStatement("select * from products");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            Category category = Category.valueOf(resultSet.getString(3));
            Double price = resultSet.getDouble(4);
            int quantity = resultSet.getInt(5);
            String description = resultSet.getString(5);
            products.add(new Product(id, name, category, price, quantity, description));
        }

        return products;
    }

    public static void relationshipOfTable(int productId, int userId) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("select * from userproducts where productId = ? and userId = ?");
        preparedStatement.setInt(1, productId);
        preparedStatement.setInt(2, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        int quantity = 0;
        while (resultSet.next()) {
            quantity = resultSet.getInt(4);

        }
        if (quantity > 0) {
            PreparedStatement preparedStatementQuantity = connectionDB().prepareStatement("update userproducts set quantity=quantity+1 where userId = ? and productId = ?");
            preparedStatementQuantity.setInt(1, userId);
            preparedStatementQuantity.setInt(2, productId);

            preparedStatementQuantity.executeUpdate();

        } else {
            PreparedStatement preparedStatementAdd = connectionDB().prepareStatement("insert into userproducts (productId,userId,quantity,progress) values(?,?,?,?)");
            preparedStatementAdd.setInt(1, productId);
            preparedStatementAdd.setInt(2, userId);
            preparedStatementAdd.setInt(3, 1);
            preparedStatementAdd.setString(4, "in_progress");

            preparedStatementAdd.executeUpdate();
        }


    }

    public static void deleteQuantityFromDB(int id) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("update products set quantity=quantity-1 where id = ? ");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }


    public static int checkQuantity(int id) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("select quantity FROM products where id=?");
        preparedStatement.setInt(1, id);
//        preparedStatement.executeQuery(); //просмотр из базы
        ResultSet resultSet = preparedStatement.executeQuery();
        int quantity = 0;
        while (resultSet.next()) {
            quantity = resultSet.getInt(1);
        }
        return quantity;

    }


    public static List<Product> getAllUsersProducts(int userId) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Product> productsCart = new ArrayList<>();
        PreparedStatement preparedStatement = connectionDB().prepareStatement("select p.id,p.name,p.category,p.price,up.quantity FROM userproducts as up left join products as p on up.productId = p.id where userId = ?  and up.progress=?");
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, "in_progress");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            Category category = Category.valueOf(resultSet.getString(3));
            Double price = resultSet.getDouble(4);
            int quantity = resultSet.getInt(5);
            productsCart.add(new Product(id, name, category, price, quantity));
        }

        return productsCart;
    }


    public static void createOrderInDb(int userId, String orderNumber, Date date) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("insert into orders (date,userId,orderNumber) values(?,?,?)");
        preparedStatement.setDate(1, new java.sql.Date(date.getTime()));
        preparedStatement.setInt(2, userId);
        preparedStatement.setString(3, orderNumber);
        preparedStatement.executeUpdate();

    }

    public static void addInOrderProducts(int orderId, int productId, int quantity) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("insert into orderproducts (productId,quantity,orderId) values(?,?,?)");
        preparedStatement.setInt(1, productId);
        preparedStatement.setInt(2, quantity);
        preparedStatement.setInt(3, orderId);
        preparedStatement.executeUpdate();

    }

    public static void payCart(int userId) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement(" update userproducts set progress = 'done' where userId=? and progress = ?");
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, "in_progress");
        preparedStatement.executeUpdate();

    }


    public static boolean fillOrder(int userId, int orderId) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Product> productsCart = getAllUsersProducts(userId);
        if (productsCart.isEmpty()) {
            return false;
        }
        for (Product p : productsCart) {
            System.out.println(p);
            addInOrderProducts(orderId, p.getId(), p.getQuantity());
        }
        return true;


    }


    public static Integer getOrderId(int userId, String orderNumber) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("select * from orders where userId = ? and orderNumber = ? ");
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, orderNumber);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            return id;

        }
        return null;
    }

    public static List<Product> getProductsFromCartInHistory(int userId, String orderNumber) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Product> orders = new ArrayList<>();
        PreparedStatement preparedStatement = connectionDB()
                .prepareStatement("select p.id, p.name,p.category, p.price, op.quantity, p.description from orders as o inner join orderproducts as op on o.id=op.orderId " +
                        "inner join products as p on p.id=op.productId where o.orderNumber=? and userId = ?");
        preparedStatement.setString(1, orderNumber);
        preparedStatement.setInt(2, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            Category category = Category.valueOf(resultSet.getString(3));
            Double price = resultSet.getDouble(4);
            int quantity = resultSet.getInt(5);
            orders.add(new Product(id, name, category, price, quantity));
        }

        return orders;

    }

    public static ArrayList<Order> getOrdersForHistory(int userId) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ArrayList<Order> orders = new ArrayList<>();
        PreparedStatement preparedStatement = connectionDB().prepareStatement("select id,date,orderNumber from orders where userId = ?");
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            Date date = resultSet.getDate(2);
            String orderNumber = resultSet.getString(3);
            orders.add(new Order(id, date, orderNumber));
        }
        return orders;


    }

    public static void addCartNumber(int userId, Cards card) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("insert into cardnumbers (cardNumber,cvc,email,userId) values(?,?,?,?)");
        preparedStatement.setString(1, card.getCardNumber());
        preparedStatement.setString(2, card.getCvc());
        preparedStatement.setString(3, card.getEmail());
        preparedStatement.setInt(4, userId);
        preparedStatement.executeUpdate();


    }

    public static void deleteProductFromCart(int userId, int productId) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement(" delete from userproducts where userId=? and productId =? and progress=?");
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, productId);
        preparedStatement.setString(3, "in_progress");
        preparedStatement.executeUpdate();

    }

    public static void deleteQuantityUserProductFromDB(int userId, int productId) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("update userproducts set quantity=quantity-1 where productId = ? and userId = ? and progress = ?");
        preparedStatement.setInt(1, productId);
        preparedStatement.setInt(2, userId);
        preparedStatement.setString(3, "in_progress");
        preparedStatement.executeUpdate();
    }
    public static void addQuantityUserProductFromDB(int userId, int productId) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("update userproducts set quantity=quantity+1 where productId = ? and userId = ? and progress = ?");
        preparedStatement.setInt(1, productId);
        preparedStatement.setInt(2, userId);
        preparedStatement.setString(3, "in_progress");
        preparedStatement.executeUpdate();
    }

    public static void deleteQuantityProductFromDB( int productId) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("update products set quantity=quantity-1 where id = ? ");
        preparedStatement.setInt(1, productId);
        preparedStatement.executeUpdate();
    }
    public static void addQuantityProductFromDB( int productId) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = connectionDB().prepareStatement("update products set quantity=quantity+1 where id = ?");
        preparedStatement.setInt(1, productId);
        preparedStatement.executeUpdate();
    }

//    delete from userproducts where userId=15 and productId =1 and progress='in_progress';
}
