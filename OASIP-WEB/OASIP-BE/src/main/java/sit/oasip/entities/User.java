package sit.oasip.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

@Getter @Setter
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID", nullable = false)
    private Integer UserId;

    @Column(name = "UserName", nullable = false, length = 100)
    private String userName;

    @Column(name = "Email", nullable = false, length = 50)
    private String email;

    @Column(name = "Password", nullable = false, length = 100)
    private String password;

    @Lob
    @Column(name = "Role", nullable = false)
    private String role;

    @CreationTimestamp
    @Column(name = "CreatedOn", nullable = false)
    private Timestamp createdOn;

    @UpdateTimestamp
    @Column(name = "UpdatedOn", nullable = false)
    private Timestamp updatedOn;

    @OneToMany(mappedBy = "userID")
    private Set<EventCategoryOwner> eventCategoryOwners;


}