package com.example.app2;

public class Order {
     private String orderid, orderproduct, address, contact,amount,delivery_note;
     private String quantity;
     public Order(){

     }

    public Order(String orderid, String orderproduct, String address, String contact, String amount, String quantity, String delivery_note) {
        this.orderid = orderid;
        this.orderproduct = orderproduct;
        this.address = address;
        this.contact = contact;
        this.amount = amount;
        this.quantity = quantity;
        this.delivery_note = delivery_note;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderproduct() {
        return orderproduct;
    }

    public void setOrderproduct(String orderproduct) {
        this.orderproduct = orderproduct;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDelivery_note() {
        return delivery_note;
    }

    public void setDelivery_note(String delivery_note) {
        this.delivery_note = delivery_note;
    }
}
