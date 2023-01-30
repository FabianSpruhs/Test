package de.haegerconsulting.haegermanagement.business.employee;

import de.haegerconsulting.haegermanagement.business.address.Address;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Embedded
    private Address address;

    private String mail;

    private String telephoneNumber;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @Enumerated(EnumType.STRING)
    private EmployeeRole role;

    private float scheduledVacationDays;

}
