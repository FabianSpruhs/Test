package de.haegerconsulting.haegermanagement.business.client;


import de.haegerconsulting.haegermanagement.business.address.Address;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
public class ClientJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String name;

    @NotNull
    @Embedded
    private Address address;

    @NotNull
    @ManyToOne(targetEntity = EmployeeJPA.class, cascade = CascadeType.MERGE)
    private EmployeeJPA contactEmployee;

}
