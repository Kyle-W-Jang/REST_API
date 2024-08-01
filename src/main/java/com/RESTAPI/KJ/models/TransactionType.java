package com.RESTAPI.KJ.models;

public enum TransactionType {
    Sale("Sale"),
    Refund("Refund");

    private final String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getTypeDescription() {
        return type;
    }
}
