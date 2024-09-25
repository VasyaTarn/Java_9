package org.example.DAO.PositionDAO;

import org.example.DAO.CRUDInterface;
import org.example.model.Position;

public interface PositionDao extends CRUDInterface<Position> {
    public void addPositionByName(String name);
}
