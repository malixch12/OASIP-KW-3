package sit.oasip.dtos.EventCategoryDTO;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditEventcategoryDTO {

    @NotBlank(message="category name must not be blank")
    @Size(min = 1, max = 100, message="size must be between 1 and 100")
    private String EventCategoryName;

    @Size(min = 0, max = 500, message = "size must be between 0 and 500")
    private String EventCategoryDescription;

    @Range(min = 1, max = 480, message="duration must be between 1 and 480")
    private Integer EventDuration;

    public void setEventCategoryName(String name) {
        EventCategoryName = name.trim();
    }
}
