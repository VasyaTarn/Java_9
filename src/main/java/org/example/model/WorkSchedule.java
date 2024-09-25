package org.example.model;

import lombok.*;
import org.example.DAO.WorkScheduleDAO.WorkScheduleDao;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkSchedule {
    private Long id;
    private long staffId;
    private LocalDate workDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public WorkSchedule(long staffId, LocalDate workDate, LocalTime startTime, LocalTime endTime)
    {
        this.staffId = staffId;
        this.workDate = workDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
