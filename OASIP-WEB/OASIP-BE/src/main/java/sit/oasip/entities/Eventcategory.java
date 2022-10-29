package sit.oasip.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "eventcategory")
public class Eventcategory {
    @Id
    @Column(name = "EventCategoryID", nullable = false)
    private Integer eventCategoryID;


    @Column(name = "EventCategoryName", nullable = false, length = 100)
    private String eventCategoryName;

    @Column(name = "EventCategoryDescription", length = 500)
    private String eventCategoryDescription;

    @Column(name = "EventDuration", nullable = false, unique = true)
    private Integer eventDuration;

    @OneToMany(mappedBy = "eventCategoryID")
    private Set<EventCategoryOwner> eventCategoryOwners;
}