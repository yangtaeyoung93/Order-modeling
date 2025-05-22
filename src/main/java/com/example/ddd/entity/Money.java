package com.example.ddd.entity;

public class Money {
    int value;

    public Money(int value) {
        this.value = value;
    }

    public Money add(Money money) {
        return new Money(this.value + money.value);
    }
}
