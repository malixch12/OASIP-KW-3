package sit.oasip.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Getter @Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID", nullable = false)
    private Integer id;

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
    @Column(name = "CreateOn", nullable = false)
    private Instant createOn;

    @UpdateTimestamp
    @Column(name = "UpdateOn", nullable = false)
    private Instant updateOn;

    @OneToMany(mappedBy = "userID")
    private Set<EventCategoryOwner> eventCategoryOwners;
}