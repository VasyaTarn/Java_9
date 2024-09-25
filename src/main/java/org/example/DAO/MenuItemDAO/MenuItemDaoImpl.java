package org.example.DAO.MenuItemDAO;

import org.example.DAO.ConnectionFactory;
import org.example.exception.ConnectionDBException;
import org.example.model.MenuItem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDaoImpl implements MenuItemDao{

    @Override
    public void updatePriceByItemType(long itemType, BigDecimal newPrice) {
        String sql = "UPDATE public.MenuItems SET price = ? WHERE item_type_id = ?";

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, newPrice);
            ps.setLong(2, itemType);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Price updated successfully for itemType: " + itemType);
            } else {
                System.out.println("No items found for itemType: " + itemType);
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void deleteByItemType(long itemTypeId) {
        String sql = "DELETE FROM public.MenuItems WHERE item_type_id = ?";

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setLong(1, itemTypeId);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Items with itemTypeId " + itemTypeId + " were deleted successfully.");
            } else {
                System.out.println("No items found with itemTypeId: " + itemTypeId);
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<MenuItem> getMenuItemsByType(long itemTypeId)
    {
        String sql = "SELECT item_id, name_en, name_other, item_type_id, price FROM public.MenuItems WHERE item_type_id = ?";
        List<MenuItem> menuItems = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setLong(1, itemTypeId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MenuItem menuItem = new MenuItem(
                            rs.getLong("item_id"),
                            rs.getString("name_en"),
                            rs.getString("name_other"),
                            rs.getLong("item_type_id"),
                            rs.getBigDecimal("price")
                    );
                    menuItems.add(menuItem);
                }
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }

        return menuItems;
    }

    @Override
    public void save(MenuItem menuItem) {
        String sql =  "INSERT INTO public.MenuItems (name_en, name_other, item_type_id, price) " + "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, menuItem.getNameEn());
            ps.setString(2, menuItem.getNameOther());
            ps.setLong(3, menuItem.getItemType());
            ps.setBigDecimal(4, menuItem.getPrice());
            ps.executeUpdate();

            System.out.println("Menu item added successfully: " + menuItem.getNameEn());
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<MenuItem> menuItems) {
        String sql = "INSERT INTO public.MenuItems (name_en, name_other, item_type_id, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            for (MenuItem menuItem : menuItems) {
                ps.setString(1, menuItem.getNameEn());
                ps.setString(2, menuItem.getNameOther());
                ps.setLong(3, menuItem.getItemType());
                ps.setBigDecimal(4, menuItem.getPrice());
                ps.addBatch();
            }
            ps.executeBatch();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(MenuItem menuItem) {
        String sql = "UPDATE public.MenuItems SET name_en = ?, name_other = ?, item_type_id = ?, price = ? WHERE item_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, menuItem.getNameEn());
            ps.setString(2, menuItem.getNameOther());
            ps.setLong(3, menuItem.getItemType());
            ps.setBigDecimal(4, menuItem.getPrice());
            ps.setLong(5, menuItem.getId());

            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(MenuItem menuItem) {
        String sql = "DELETE FROM public.MenuItems WHERE item_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, menuItem.getId());

            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<MenuItem> findAll() {
        String sql = "SELECT * FROM public.MenuItems";
        List<MenuItem> menuItems = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet result = ps.executeQuery()) {

            while (result.next()) {
                MenuItem menuItem = new MenuItem(
                        result.getString("name_en"),
                        result.getString("name_other"),
                        result.getLong("item_type_id"),
                        result.getBigDecimal("price")
                );
                menuItem.setId(result.getLong("item_id"));
                menuItems.add(menuItem);
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }

        return menuItems;
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM public.MenuItems";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
