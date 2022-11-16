package sit.oasip.dtos.EventCategoryDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetEventCategoryDTO {
    private Integer EventCategoryID;
    private String EventCategoryName;
    private String EventCategoryDescription;
    private Integer EventDuration;
}
