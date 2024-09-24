package org.example.model;

import lombok.*;
import org.example.DAO.MenuItemDAO.MenuItemDao;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {
    private Long id;
    private String nameEn;
    private String nameOther;
    private long itemType;
    private BigDecimal price;

    public MenuItem(String nameEn, String nameOther, long itemType, BigDecimal price)
    {
        this.nameEn = nameEn;
        this.nameOther = nameOther;
        this.itemType = itemType;
        this.price = price;
    }
}

