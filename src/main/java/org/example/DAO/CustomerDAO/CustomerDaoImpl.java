package org.example.DAO.CustomerDAO;

import org.example.DAO.ConnectionFactory;
import org.example.exception.ConnectionDBException;
import org.example.model.Customer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDaoImpl implements CustomerDao{
    public void AddCustomer(Customer customer)
    {
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

    public void deleteCustomer(long id)
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
}
