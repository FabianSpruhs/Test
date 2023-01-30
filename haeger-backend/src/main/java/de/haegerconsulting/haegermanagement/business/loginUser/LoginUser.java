package de.haegerconsulting.haegermanagement.business.loginUser;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private LoginUserRoles userRole;

    @OneToOne(cascade = CascadeType.MERGE)
    private EmployeeJPA employee;

}
