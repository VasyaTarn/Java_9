package org.example.DAO.CustomerDAO;

import org.example.DAO.CRUDInterface;
import org.example.model.Customer;

import java.math.BigDecimal;

public interface CustomerDao extends CRUDInterface<Customer> {
    public void updateDiscount(long id, BigDecimal discount);
    public void deleteCustomerById(long id);
}
