package sit.int204.classicmodelsservice2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "customerNumber", nullable = false)
    private Integer id;
    @Column(name = "customerName", nullable = false, length = 50)
    private String customerName;
    @Column(name = "contactLastName", nullable = false, length = 50)
    private String contactLastName;
    @Column(name = "contactFirstName", nullable = false, length = 50)
    private String contactFirstName;
    @Column(name = "phone", nullable = false, length = 50)
    private String phone;
    @Column(name = "addressLine1", nullable = false, length = 50)
    private String addressLine1;
    @Column(name = "addressLine2", length = 50)
    private String addressLine2;
    @Column(name = "city", nullable = false, length = 50)
    private String city;
    @Column(name = "state", length = 50)
    private String state;
    @Column(name = "postalCode", length = 15)
    private String postalCode;
    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salesRepEmployeeNumber")
    private Employee salesRepEmployee;
    @Column(name = "creditLimit", precision = 10, scale = 2)
    private BigDecimal creditLimit;
}