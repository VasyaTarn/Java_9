package org.example.DAO.MenuItemDAO;

import org.example.DAO.CRUDInterface;
import org.example.model.MenuItem;

import java.math.BigDecimal;
import java.util.List;

public interface MenuItemDao extends CRUDInterface<MenuItem> {
    public void updatePriceByItemType(long itemType, BigDecimal newPrice);
    public void deleteByItemType(long itemTypeId);
    public List<MenuItem> getMenuItemsByType(long itemTypeId);
}
