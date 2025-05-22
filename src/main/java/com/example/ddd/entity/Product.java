package com.example.ddd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Product {
    private String name;
    private Money price;
    private String detail;

    public Product(String name, Money price, String detail) {
        this.name = name;
        this.price = price;
        this.detail = detail;
    }
}
