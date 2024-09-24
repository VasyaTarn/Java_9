package org.example.DAO.StaffDAO;

import org.example.model.MenuItem;
import org.example.model.Staff;

import java.util.List;

public interface StaffDao {
    public void addStaff(Staff staff);
    public void updateEmailByConfectioner(String email);
    public void updatePhoneByBarista(String phone);
    public void deleteStaffByWaiter(long staffId);
    public void deleteStaffByBarista(long staffId);
    public List<Staff> getStuffByPosition(long positionId);
}
