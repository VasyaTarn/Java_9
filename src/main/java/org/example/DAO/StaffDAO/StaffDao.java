package org.example.DAO.StaffDAO;

import org.example.DAO.CRUDInterface;
import org.example.model.MenuItem;
import org.example.model.Staff;

import java.util.List;

public interface StaffDao extends CRUDInterface<Staff> {
    public void updateEmailByConfectioner(String email);
    public void updatePhoneByBarista(String phone);
    public void deleteStaffByWaiter(long staffId);
    public void deleteStaffByBarista(long staffId);
    public List<Staff> getStuffByPosition(long positionId);
}
