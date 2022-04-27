package sit.int204.classicmodelsservice2.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleEmployeeDTO {
    private String firstName;
    private String lastName;

    public String getName() {
        return firstName + ' ' + lastName;
    }

}
