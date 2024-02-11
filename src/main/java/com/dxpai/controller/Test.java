package com.dxpai.controller;

import com.google.gson.Gson;
import com.dxpai.model.Transaction;

import java.util.Arrays;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        Transaction t1 = new Transaction(5.0,"CompleteSave","2024-01-12", UUID.randomUUID().toString());
        Transaction t2 = new Transaction(5.7,"Amazon","2024-01-13",UUID.randomUUID().toString());
        Transaction t3 = new Transaction(5.9,"Uber","2024-01-14",UUID.randomUUID().toString());
        Transaction t4 = new Transaction(5.7,"GiffGaff","2024-01-15",UUID.randomUUID().toString());
        Transaction t5 = new Transaction(5.0,"CompleteSave","2024-02-12",UUID.randomUUID().toString());
        Transaction t6 = new Transaction(5.8,"Oracle","2024-02-18",UUID.randomUUID().toString());
        Transaction t7 = new Transaction(15.0,"Pret Manger","2024-02-19",UUID.randomUUID().toString());
        Transaction t8 = new Transaction(15.0,"Sainsburry","2024-02-19",UUID.randomUUID().toString());
        Transaction t9 = new Transaction(15.0,"Tesco","2024-02-19",UUID.randomUUID().toString());
        Transaction t10 = new Transaction(5.0,"CompleteSave","2024-03-12",UUID.randomUUID().toString());
        Transaction t11 = new Transaction(5.1,"Starbucks","2024-03-11",UUID.randomUUID().toString());
        System.out.println(new Gson().toJson(Arrays.asList(t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11)));
    }
}
