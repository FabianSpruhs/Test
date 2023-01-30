package de.haegerconsulting.haegermanagement.business.workingHour;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WorkingHourJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @ManyToOne(targetEntity = EmployeeJPA.class, cascade = CascadeType.MERGE)
    private EmployeeJPA employee;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate workingDay;

    @NotNull
    private float workingHours;

    @NotNull
    @Enumerated(EnumType.STRING)
    private WorkingHourStatus workingHourStatus;

}
