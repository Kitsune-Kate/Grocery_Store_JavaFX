package com.example.demo;

import java.util.Date;

public class Order {
    private int id;
    private Date date;
    private int userId;
    private String orderNumber;
    private int orderId;

    public Order(int id, Date date, String orderNumber) {
        this.id = id;
        this.date = date;
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "Order{" +
                "date=" + date +
                ", userId=" + userId +
                ", orderNumber=" + orderNumber +
                ", orderId=" + orderId +
                '}';
    }

    public Order(Date date, int userId, String orderNumber, int orderId) {
        this.date = date;
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
