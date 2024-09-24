package org.example.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private BigDecimal discount;

    public Customer(String firstName, String lastName, String patronymic, LocalDate birthDate, String phone, String email, BigDecimal discount)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.discount = discount;
    }
}
