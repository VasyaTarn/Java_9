package org.example.DAO.OrderDAO;

import org.example.DAO.ConnectionFactory;
import org.example.exception.ConnectionDBException;
import org.example.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao{
    @Override
    public void save(Order order) {
        String sql = "INSERT INTO public.Orders (customer_first_name, customer_last_name, order_date, total_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, order.getCustomerFirstName());
            ps.setString(2, order.getCustomerLastName());
            ps.setTimestamp(3, Timestamp.valueOf(order.getOrderDate()));
            ps.setBigDecimal(4, order.getTotalPrice());
            ps.executeUpdate();

        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<Order> orders) {
        String sql = "INSERT INTO public.Orders (customer_first_name, customer_last_name, order_date, total_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Order order : orders) {
                ps.setString(1, order.getCustomerFirstName());
                ps.setString(2, order.getCustomerLastName());
                ps.setTimestamp(3, Timestamp.valueOf(order.getOrderDate()));
                ps.setBigDecimal(4, order.getTotalPrice());
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
    public void update(Order order) {
        String sql = "UPDATE public.Orders SET customer_first_name = ?, customer_last_name = ?, order_date = ?, total_price = ? WHERE order_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, order.getCustomerFirstName());
            ps.setString(2, order.getCustomerLastName());
            ps.setTimestamp(3, Timestamp.valueOf(order.getOrderDate()));
            ps.setBigDecimal(4, order.getTotalPrice());
            ps.setLong(5, order.getId());
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Order order) {
        String sql = "DELETE FROM public.Orders WHERE order_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, order.getId());
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Order> findAll() {
        String sql = "SELECT * FROM public.Orders";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet result = ps.executeQuery()) {

            while (result.next()) {
                Order order = new Order(
                        result.getString("customer_first_name"),
                        result.getString("customer_last_name"),
                        result.getTimestamp("order_date").toLocalDateTime(),
                        result.getBigDecimal("total_price")
                );
                order.setId(result.getLong("order_id"));
                orders.add(order);
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
        return orders;
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM public.Orders";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
