package com.dxpai.model;

import lombok.Data;

@Data
public class Transaction {

    private Double amount;
    private String merchantName;
    private String date;
    private String trxId;

    public Transaction(Double amount, String merchantName, String date,String trxID) {
        this.amount = amount;
        this.merchantName = merchantName;
        this.date = date;
        this.trxId = trxID;
    }



}
