package com.example.ddd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderLine {
    private Product product;
    private Money price;
    private int quantity;
    private Money amounts;

    public OrderLine(Product product, Money price, int quantity, Money amounts) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.amounts = amounts;
    }
}
