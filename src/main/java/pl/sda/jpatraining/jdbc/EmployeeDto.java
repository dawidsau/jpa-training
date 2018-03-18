package pl.sda.jpatraining.jdbc;


import lombok.*;

import java.sql.Date;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    int employee_id;
    String employee_name;
    String salary;
    Date hiredate;
}