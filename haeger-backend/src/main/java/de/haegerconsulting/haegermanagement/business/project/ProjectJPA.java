package de.haegerconsulting.haegermanagement.business.project;


import de.haegerconsulting.haegermanagement.business.client.ClientJPA;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProjectJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = ClientJPA.class)
    private ClientJPA client;

    @NotNull
    private String name;
}
