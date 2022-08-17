package sit.oasip.dtos;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

// @Unique(columns = {
//     @UniqueColumn(fields = "EventCategoryName")
// })
public class SimpleEventcategoryDTO {
    private Integer EventCategoryID;

    @NotBlank(message="category name must not be blank")
    @Size(min = 1, max = 100, message="size must be between 1 and 100")
    private String EventCategoryName;
    private String EventCategoryDescription;

    @Range(min = 1, max = 480, message="duration must be between 1 and 480")
    private Integer EventDuration;
}
