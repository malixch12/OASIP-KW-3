package sit.int204.classicmodelsservice2.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleEventcategoryDTO {
    private Integer EventCategoryID;
    private String EventCategoryName;
    private String EventCategoryDescription;

    @Min(value = 1, message = "duration must at least 1")
    @Max(value = 480, message = "duration must not more than 480")
    private Integer EventDuration;
}
