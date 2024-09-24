package org.example.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private Long id;
    private String customerFirstName;
    private String customerLastName;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
}
