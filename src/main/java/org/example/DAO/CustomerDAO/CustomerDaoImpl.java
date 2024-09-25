package org.example.DAO.CustomerDAO;

import org.example.DAO.ConnectionFactory;
import org.example.exception.ConnectionDBException;
import org.example.model.Customer;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao{

    @Override
    public void updateDiscount(long id, BigDecimal discount)
    {
        String sql = "UPDATE public.Customers SET discount = ? WHERE customer_id = ?";

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, discount);
            ps.setLong(2, id);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Discount updated successfully for customer: " + id + " " + discount);
            } else {
                System.out.println("Customer not found");
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void deleteCustomerById(long id)
    {
        String sql = "DELETE FROM public.Customers WHERE customer_id = ?";

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setLong(1, id);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Customer with id " + id + " were deleted successfully.");
            } else {
                System.out.println("No customer found with id: " + id);
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void save(Customer customer) {
        String sql =  "INSERT INTO public.Customers (first_name, last_name, patronymic, birth_date, phone, email, discount) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getPatronymic());
            ps.setDate(4, Date.valueOf(customer.getBirthDate()));
            ps.setString(5, customer.getPhone());
            ps.setString(6, customer.getEmail());
            ps.setBigDecimal(7, customer.getDiscount());
            ps.executeUpdate();

            System.out.println("Customer added successfully: " + customer.getFirstName() + " " + customer.getLastName());
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<Customer> customers) {
        String sql = "INSERT INTO public.Customers (first_name, last_name, patronymic, birth_date, phone, email, discount) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (Customer customer : customers) {
                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                ps.setString(3, customer.getPatronymic());
                ps.setDate(4, Date.valueOf(customer.getBirthDate()));
                ps.setString(5, customer.getPhone());
                ps.setString(6, customer.getEmail());
                ps.setBigDecimal(7, customer.getDiscount());
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
    public void update(Customer customer) {
        String sql = "UPDATE public.Customers SET first_name = ?, last_name = ?, patronymic = ?, birth_date = ?, phone = ?, email = ?, discount = ? WHERE customer_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getPatronymic());
            ps.setDate(4, Date.valueOf(customer.getBirthDate()));
            ps.setString(5, customer.getPhone());
            ps.setString(6, customer.getEmail());
            ps.setBigDecimal(7, customer.getDiscount());
            ps.setLong(8, customer.getId());
            ps.execute();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Customer customer) {
        String sql = "DELETE FROM public.Customers WHERE customer_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, customer.getId());
            ps.execute();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM public.Customers";
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet result = ps.executeQuery()) {

            while (result.next()) {
                Customer customer = new Customer();
                customer.setId(result.getLong("customer_id"));
                customer.setFirstName(result.getString("first_name"));
                customer.setLastName(result.getString("last_name"));
                customer.setPatronymic(result.getString("patronymic"));
                customer.setBirthDate(result.getDate("birth_date").toLocalDate());
                customer.setPhone(result.getString("phone"));
                customer.setEmail(result.getString("email"));
                customer.setDiscount(result.getBigDecimal("discount"));
                customers.add(customer);
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }

        return customers;
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM public.Customers";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
