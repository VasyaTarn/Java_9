package org.example.DAO.itemTypeDAO;

import org.example.DAO.CRUDInterface;
import org.example.model.ItemType;

public interface ItemTypeDao extends CRUDInterface<ItemType> {
    public void addItemTypeByName(String name);
}
