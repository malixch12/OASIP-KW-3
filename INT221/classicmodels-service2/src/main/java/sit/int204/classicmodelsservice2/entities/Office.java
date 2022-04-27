package sit.int204.classicmodelsservice2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity @Table(name = "offices")
public class Office {
    @Id
    @Column(name = "officeCode", nullable = false, length = 10)
    private String id;
    @Column(name = "city", nullable = false, length = 50)
    private String city;
    @Column(name = "phone", nullable = false, length = 50)
    private String phone;
    @Column(name = "addressLine1", nullable = false, length = 50)
    private String addressLine1;
    @Column(name = "addressLine2", length = 50)
    private String addressLine2;
    @Column(name = "state", length = 50)
    private String state;
    @Column(name = "country", nullable = false, length = 50)
    private String country;
    @Column(name = "postalCode", nullable = false, length = 15)
    private String postalCode;
    @Column(name = "territory", nullable = false, length = 10)
    private String territory;
    @JsonIgnore
    @OneToMany(mappedBy = "office")
    private Set<Employee> employees = new LinkedHashSet<>();
}