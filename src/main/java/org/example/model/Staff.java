package org.example.model;

import lombok.*;
import org.example.DAO.StaffDAO.StaffDao;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phone;
    private String email;
    private long position;

    public Staff(String firstName, String lastName, String patronymic, String phone, String email, long position)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.phone = phone;
        this.email = email;
        this.position = position;
    }
}
