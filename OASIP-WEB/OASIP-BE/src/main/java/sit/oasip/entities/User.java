package sit.oasip.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID", nullable = false)
    private Integer userId;

    @Column(name = "UserName", nullable = false, length = 100)
    private String userName;

    @Column(name = "Email", nullable = false, length = 50)
    private String email;

    @Lob
    @Column(name = "Role", nullable = false)
    private String role;

    @CreationTimestamp
    @Column(name = "CreateOn", nullable = false)
    private Timestamp createOn;

    @UpdateTimestamp
    @Column(name = "UpdateOn", nullable = false)
    private Timestamp updateOn;

    public Timestamp getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(Timestamp updateOn) {
        this.updateOn = updateOn;
    }

    public Timestamp getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Timestamp createOn) {
        this.createOn = createOn;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return userId;
    }

    public void setId(Integer id) {
        this.userId = id;
    }
}