package org.example.DAO.StaffDAO;

import org.example.DAO.ConnectionFactory;
import org.example.exception.ConnectionDBException;
import org.example.model.MenuItem;
import org.example.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDaoImpl implements StaffDao {
    public void addStaff(Staff staff) {
        String sql =  "INSERT INTO public.Staff (first_name, last_name, patronymic, phone, email, position_id) " + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, staff.getFirstName());
            ps.setString(2, staff.getLastName());
            ps.setString(3, staff.getPatronymic());
            ps.setString(4, staff.getPhone());
            ps.setString(5, staff.getEmail());
            ps.setLong(6, staff.getPosition());
            ps.executeUpdate();

            System.out.println("Staff member added successfully: " + staff.getFirstName() + " " + staff.getLastName());
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void updateEmailByConfectioner(String email)
    {
        String sql = "UPDATE public.Staff SET email = ? WHERE position_id = 3";

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Email updated successfully for confectioner: " + email);
            } else {
                System.out.println("No confectioners found");
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void updatePhoneByBarista(String phone)
    {
        String sql = "UPDATE public.Staff SET phone = ? WHERE position_id = 1";

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, phone);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Phone updated successfully for barista: " + phone);
            } else {
                System.out.println("No barista found");
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void deleteStaffByWaiter(long staffId)
    {
        String sql = "DELETE FROM public.Staff WHERE staff_id = ? AND position_id = 2";

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setLong(1, staffId);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Waiter with id " + staffId + " were deleted successfully.");
            } else {
                System.out.println("No waiter found with id: " + staffId);
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void deleteStaffByBarista(long staffId)
    {
        String sql = "DELETE FROM public.Staff WHERE staff_id = ? AND position_id = 1";

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setLong(1, staffId);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Barista with id " + staffId + " were deleted successfully.");
            } else {
                System.out.println("No barista found with id: " + staffId);
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }


    public List<Staff> getStuffByPosition(long positionId)
    {
        String sql = "SELECT staff_id, first_name, last_name, patronymic, phone, email, position_id FROM public.Staff WHERE position_id = ?";
        List<Staff> stuff = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setLong(1, positionId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Staff menuItem = new Staff(
                            rs.getLong("staff_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("patronymic"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getLong("position_id")
                    );
                    stuff.add(menuItem);
                }
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }

        return stuff;
    }
}
