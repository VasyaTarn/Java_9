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
    public void addMenuItem(MenuItem item)
    {
        String sql =  "INSERT INTO public.MenuItems (name_en, name_other, item_type_id, price) " + "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, item.getNameEn());
            ps.setString(2, item.getNameOther());
            ps.setLong(3, item.getItemType());
            ps.setBigDecimal(4, item.getPrice());
            ps.executeUpdate();

            System.out.println("Menu item added successfully: " + item.getNameEn());
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

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
}
