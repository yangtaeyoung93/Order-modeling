package com.example.ddd.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
@EqualsAndHashCode
@Getter
public class Order {
    private OrderState state;
    private ShippingInfo shippingInfo;
    private List<OrderLine> orderLines;

    private Money totalAmounts;

    public Order(ShippingInfo shippingInfo, List<OrderLine> orderLines) {
        setShippingInfo(shippingInfo);
        setOrderLines(orderLines);
    }


    public void changeShippingInfo(ShippingInfo newShippingInfo){
        if(!isShippingChangeable()){
            throw new IllegalArgumentException("can't change shipping in" + state);
        }

        this.shippingInfo = newShippingInfo;
    }

    /**
     * 취소는 출고 전에만 가능
     */
    public void cancle(){
        verifyNotYetShipped();
        this.state = OrderState.CANCELED;
    }

    /**
     * 상품 출고 전 인지
     */
    private void verifyNotYetShipped() {

        if (state != OrderState.PAYMENT_WAITING && state != OrderState.PREPARING) {
            throw new IllegalArgumentException("aleady shipped");
        }

    }

    /**
     * 배송 취소 가능한 상태인지
     * @return
     */
    private boolean isShippingChangeable(){
        return state == OrderState.PAYMENT_WAITING || state == OrderState.PREPARING;
    }

    /**
     * 최소 한 종류 이상의 상품을 주문을 했는지
     * @param orderLines
     */
    private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
        Optional.ofNullable(orderLines)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("no OrderLine"));
    }

    private void calculateTotalAmounts(){
        int sum = orderLines.stream().mapToInt(x -> x.getAmounts().value).sum();
        this.totalAmounts = new Money(sum);
    }

    /**
     * 배송지 정보
     * @param shippingInfo
     */
    private void setShippingInfo(ShippingInfo shippingInfo) {
        Optional.ofNullable(shippingInfo).orElseThrow(() -> new IllegalArgumentException("No Shipping Info"));
        this.shippingInfo = shippingInfo;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
    }
}
