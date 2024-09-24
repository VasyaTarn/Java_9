package org.example.model;

import lombok.*;

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
}
