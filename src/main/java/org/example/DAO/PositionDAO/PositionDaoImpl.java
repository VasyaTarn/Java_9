package org.example.DAO.PositionDAO;

import org.example.DAO.ConnectionFactory;
import org.example.exception.ConnectionDBException;
import org.example.model.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionDaoImpl implements PositionDao{
    @Override
    public void addPositionByName(String name)
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

    @Override
    public void save(Position position) {
        String sql = "INSERT INTO public.Position (name) VALUES (?)";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, position.getName());
            ps.executeUpdate();

        }
        catch (ConnectionDBException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<Position> positions) {
        String sql = "INSERT INTO public.Position (name) VALUES (?)";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Position position : positions) {
                ps.setString(1, position.getName());
                ps.addBatch();
            }
            ps.executeBatch();
        }
        catch (ConnectionDBException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Position position) {
        String sql = "UPDATE public.Position SET name = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, position.getName());
            ps.setLong(2, position.getId());
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Position position) {
        String sql = "DELETE FROM public.Position WHERE id = ?";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, position.getId());
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Position> findAll() {
        String sql = "SELECT * FROM public.Position";
        List<Position> positions = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet result = ps.executeQuery()) {

            while (result.next()) {
                Position position = new Position(result.getLong("id"), result.getString("name"));
                positions.add(position);
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.err.println(e.getMessage());
        }

        return positions;
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM public.Position";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
