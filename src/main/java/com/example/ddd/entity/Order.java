package com.example.ddd.entity;

import java.util.List;
import java.util.Optional;

public class Order {
    private OrderState state;
    private ShippingInfo shippingInfo;
    private List<OrderLine> orderLines;

    private Money totalAmounts;

    public void changeShippingInfo(ShippingInfo newShippingInfo){
        if(!isShippingChangeable()){
            throw new IllegalArgumentException("can't change shipping in" + state);
        }

        this.shippingInfo = newShippingInfo;
    }

    private boolean isShippingChangeable(){
        return state == OrderState.PAYMENT_WAITING || state == OrderState.PREPARING;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
    }

    private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
        Optional.ofNullable(orderLines)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("no OrderLine"));
    }

    private void calculateTotalAmounts(){
        this.totalAmounts = new Money(orderLines.stream().mapToInt(x -> x.getAmounts()).sum());
    }
}
