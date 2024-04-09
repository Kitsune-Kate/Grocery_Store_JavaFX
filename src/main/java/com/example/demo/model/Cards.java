package com.example.demo.model;

public class Cards {

    private int id;
    private String cardNumber;
    private String cvc;
    private String email;
    private int userId;

    public Cards(int id, String cardNumber, String cvc, String email, int userId) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.email = email;
        this.userId = userId;
    }

    public Cards(String cardNumber, String cvc, String email) {
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
