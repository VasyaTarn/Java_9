package org.example.DAO.itemTypeDAO;

import org.example.DAO.ConnectionFactory;
import org.example.exception.ConnectionDBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemTypeDaoImpl implements ItemTypeDao {
    public void addItemType(String name) {
        String sql = "INSERT INTO public.ItemTypes (name) VALUES (?)";

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, name);
            ps.executeUpdate();
            System.out.println("Item type added successfully: " + name);
        }
        catch (ConnectionDBException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
