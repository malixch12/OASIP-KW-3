package sit.int204.classicmodelsservice2.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;


@Getter
@Setter
public class SimpleCustomerDTO {


    private String customerName;
    private String phone;
    private String city;
    private String country;
 //   private String SalesPerson;
    private SimpleEmployeeDTO salesRepEmployee;

 


}
