package org.example.DAO.itemTypeDAO;

import org.example.DAO.ConnectionFactory;
import org.example.exception.ConnectionDBException;
import org.example.model.ItemType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemTypeDaoImpl implements ItemTypeDao {
    @Override
    public void addItemTypeByName(String name) {
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

    @Override
    public void save(ItemType itemType) {
        String sql = "INSERT INTO public.ItemTypes (name) VALUES (?)";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, itemType.getName());
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<ItemType> itemTypes) {
        String sql = "INSERT INTO public.ItemTypes (name) VALUES (?)";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            for (ItemType itemType : itemTypes) {
                ps.setString(1, itemType.getName());
                ps.addBatch();
            }
            ps.executeBatch();
        }
        catch (ConnectionDBException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(ItemType itemType) {
        String sql = "UPDATE public.ItemTypes SET name = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, itemType.getName());
            ps.setLong(2, itemType.getId());
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(ItemType itemType) {
        String sql = "DELETE FROM public.ItemTypes WHERE id = ?";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, itemType.getId());
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<ItemType> findAll() {
        String sql = "SELECT * FROM public.ItemTypes";
        List<ItemType> itemTypes = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet result = ps.executeQuery()) {

            while (result.next()) {
                ItemType itemType = new ItemType();
                itemType.setId(result.getLong("id"));
                itemType.setName(result.getString("name"));
                itemTypes.add(itemType);
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.err.println(e.getMessage());
        }

        return itemTypes;
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM public.ItemTypes";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
