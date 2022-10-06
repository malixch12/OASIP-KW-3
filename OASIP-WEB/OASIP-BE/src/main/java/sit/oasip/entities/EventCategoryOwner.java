package sit.oasip.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "EventCategoryOwner")
public class EventCategoryOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EventCategoryOwnerID", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "EventCategoryID")
    private Eventcategory eventCategoryID;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User userID;

}