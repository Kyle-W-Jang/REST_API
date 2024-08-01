package com.RESTAPI.KJ.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {

    //id A unique identifier.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //date	The date an order occurred.
    @Column(nullable = false)
    private LocalDate date;

    //amount	The cost of the order.
    @Column(nullable = false)
    private Double amount;

    //currencyCode	The ISO 4217 currency code.
    @Column(nullable = false)
    @Size(min = 3, max = 3)
    private String currencyCode;


    //transactionType --> Sale or Refund.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    public Order() {
    }

    public Order(long id, LocalDate date, Double amount, String currencyCode, TransactionType transactionType) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.transactionType = transactionType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", transactionType=" + transactionType +
                '}';
    }
}
