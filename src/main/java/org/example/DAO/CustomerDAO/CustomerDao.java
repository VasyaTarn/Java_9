package org.example.DAO.CustomerDAO;

import org.example.model.Customer;

import java.math.BigDecimal;

public interface CustomerDao{
    public void AddCustomer(Customer customer);
    public void updateDiscount(long id, BigDecimal discount);
    public void deleteCustomer(long id);
}
