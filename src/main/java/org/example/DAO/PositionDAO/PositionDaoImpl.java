package org.example.DAO.PositionDAO;

import org.example.DAO.ConnectionFactory;
import org.example.exception.ConnectionDBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PositionDaoImpl implements PositionDao{
    public void addPosition(String name)
    {
        String sql = "INSERT INTO public.Position (name) VALUES (?)";

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, name);
            ps.executeUpdate();
            System.out.println("Position added successfully: " + name);
        }
        catch (ConnectionDBException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
