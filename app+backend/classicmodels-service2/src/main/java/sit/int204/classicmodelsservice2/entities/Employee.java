package sit.int204.classicmodelsservice2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity @Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "employeeNumber", nullable = false)
    private Integer id;
    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;
    @Column(name = "firstName", nullable = false, length = 50)
    private String firstName;
    @Column(name = "extension", nullable = false, length = 10)
    private String extension;
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    //    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "officeCode")
    private Office office;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "reportsTo")
    private Employee reportsTo;
    @Column(name = "jobTitle", nullable = false, length = 50)
    private String jobTitle;
    @JsonIgnore
    @OneToMany(mappedBy = "salesRepEmployee")
    private Set<Customer> customers = new LinkedHashSet<>();
}