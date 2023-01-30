package de.haegerconsulting.haegermanagement.business.sickNote;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SickNoteJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @ManyToOne(targetEntity = EmployeeJPA.class, cascade = CascadeType.MERGE)
    private EmployeeJPA employee;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate beginDate;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

}
