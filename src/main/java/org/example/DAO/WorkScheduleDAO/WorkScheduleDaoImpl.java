package org.example.DAO.WorkScheduleDAO;

import org.example.DAO.ConnectionFactory;
import org.example.exception.ConnectionDBException;
import org.example.model.WorkSchedule;
import java.util.List;
import java.util.ArrayList;

import java.sql.*;

public class WorkScheduleDaoImpl implements WorkScheduleDao{
    @Override
    public void save(WorkSchedule workSchedule) {
        String sql = "INSERT INTO public.WorkSchedule (staff_id, work_date, start_time, end_time) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, workSchedule.getStaffId());
            ps.setDate(2, Date.valueOf(workSchedule.getWorkDate()));
            ps.setTime(3, Time.valueOf(workSchedule.getStartTime()));
            ps.setTime(4, Time.valueOf(workSchedule.getEndTime()));
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<WorkSchedule> workSchedules) {
        String sql = "INSERT INTO public.WorkSchedule (staff_id, work_date, start_time, end_time) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            for (WorkSchedule workSchedule : workSchedules) {
                ps.setLong(1, workSchedule.getStaffId());
                ps.setDate(2, Date.valueOf(workSchedule.getWorkDate()));
                ps.setTime(3, Time.valueOf(workSchedule.getStartTime()));
                ps.setTime(4, Time.valueOf(workSchedule.getEndTime()));
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
    public void update(WorkSchedule workSchedule) {
        String sql = "UPDATE public.WorkSchedule SET staff_id = ?, work_date = ?, start_time = ?, end_time = ? WHERE schedule_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, workSchedule.getStaffId());
            ps.setDate(2, Date.valueOf(workSchedule.getWorkDate()));
            ps.setTime(3, Time.valueOf(workSchedule.getStartTime()));
            ps.setTime(4, Time.valueOf(workSchedule.getEndTime()));
            ps.setLong(5, workSchedule.getId());
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(WorkSchedule workSchedule) {
        String sql = "DELETE FROM public.WorkSchedule WHERE schedule_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, workSchedule.getId());
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<WorkSchedule> findAll() {
        String sql = "SELECT * FROM public.WorkSchedule";
        List<WorkSchedule> workSchedules = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet result = ps.executeQuery()) {

            while (result.next()) {
                WorkSchedule workSchedule = new WorkSchedule(
                        result.getLong("staff_id"),
                        result.getDate("work_date").toLocalDate(),
                        result.getTime("start_time").toLocalTime(),
                        result.getTime("end_time").toLocalTime()
                );
                workSchedule.setId(result.getLong("schedule_id"));
                workSchedules.add(workSchedule);
            }
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
        return workSchedules;
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM WorkSchedule";
        try (Connection conn = ConnectionFactory.getInstance().makeConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
        catch (ConnectionDBException | SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
