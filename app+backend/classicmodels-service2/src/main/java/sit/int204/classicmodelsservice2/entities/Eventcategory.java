package sit.int204.classicmodelsservice2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "eventcategory")
public class Eventcategory {
    @Id
    @Column(name = "EventCategoryID", nullable = false)
    private Integer id;

    @Column(name = "EventCategoryName", nullable = false, length = 100)
    private String eventCategoryName;

    @Column(name = "EventCategoryDescription", length = 500)
    private String eventCategoryDescription;

    @Column(name = "EventDuration", nullable = false)
    private Integer eventDuration;

    
}