package sit.oasip.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Getter @Setter
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID", nullable = false)
    private Integer ID;

    @Column(name = "UserName", nullable = false, length = 100,unique = true)
    private String name;

    @Column(name = "Email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "Password", nullable = false, length = 90)
    private String password;

    @Lob
    @Column(name = "Role", nullable = false)
    private String role;

    @CreationTimestamp
    @Column(name = "CreateOn", nullable = false)
    private Timestamp createdOn;

    @UpdateTimestamp
    @Column(name = "UpdateOn", nullable = false)
    private Timestamp updatedOn;


}