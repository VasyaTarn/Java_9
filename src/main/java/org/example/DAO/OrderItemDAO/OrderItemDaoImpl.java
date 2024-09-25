package org.example.DAO.OrderItemDAO;

import org.example.DAO.ConnectionFactory;
import org.example.exception.ConnectionDBException;
import org.example.model.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDaoImpl implements OrderItemDao {
    @Override
    public void save(OrderItem orderItem) {
        String sql = "INSERT INTO public.OrderItems (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderItem.getOrderId());
            ps.setLong(2, orderItem.getItemId());
            ps.setInt(3, orderItem.getQuantity());
            ps.setBigDecimal(4, orderItem.getPrice());
            ps.executeUpdate();

        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<OrderItem> orderItems) {
        String sql = "INSERT INTO public.OrderItems (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            for (OrderItem orderItem : orderItems) {
                ps.setLong(1, orderItem.getOrderId());
                ps.setLong(2, orderItem.getItemId());
                ps.setInt(3, orderItem.getQuantity());
                ps.setBigDecimal(4, orderItem.getPrice());
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
    public void update(OrderItem orderItem) {
        String sql = "UPDATE public.OrderItems SET order_id = ?, item_id = ?, quantity = ?, price = ? WHERE order_item_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderItem.getOrderId());
            ps.setLong(2, orderItem.getItemId());
            ps.setInt(3, orderItem.getQuantity());
            ps.setBigDecimal(4, orderItem.getPrice());
            ps.setLong(5, orderItem.getOrderItemId());
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(OrderItem orderItem) {
        String sql = "DELETE FROM public.OrderItems WHERE order_item_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderItem.getOrderItemId());
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<OrderItem> findAll() {
        String sql = "SELECT * FROM public.OrderItems";
        List<OrderItem> orderItems = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet result = ps.executeQuery()) {

            while (result.next()) {
                OrderItem orderItem = new OrderItem(
                        result.getLong("order_id"),
                        result.getLong("item_id"),
                        result.getInt("quantity"),
                        result.getBigDecimal("price")
                );
                orderItem.setOrderItemId(result.getLong("order_item_id"));
                orderItems.add(orderItem);
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }

        return orderItems;
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM public.OrderItems";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
