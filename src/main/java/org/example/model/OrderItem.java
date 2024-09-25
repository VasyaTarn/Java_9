package org.example.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    private Long orderItemId;
    private long orderId;
    private long itemId;
    private int quantity;
    private BigDecimal price;

    public OrderItem(long orderId, long itemId, int quantity, BigDecimal price)
    {
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
    }
}
