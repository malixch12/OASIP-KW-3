package sit.int204.classicmodelsservice2.services;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.classicmodelsservice2.dtos.SimpleCustomerDTO;
import sit.int204.classicmodelsservice2.entities.Customer;
import sit.int204.classicmodelsservice2.repositories.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public SimpleCustomerDTO getSimpleCustomerById(Integer id) {
        Customer customer = repository.findById(id).orElseThrow(()
                ->new ResponseStatusException(HttpStatus.NOT_FOUND,  id +" Does Not Exist !!!" ));

            return modelMapper.map(customer,SimpleCustomerDTO.class);
    }
//        return repository.findById(id).map(customer -> convertEntityToDto(customer))
//                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,  id +" Does Not Exist !!!" ));


    private SimpleCustomerDTO convertEntityToDto(Customer customer) {
        SimpleCustomerDTO simpleCustomerDTO= new SimpleCustomerDTO();
        simpleCustomerDTO.setCustomerName(customer.getCustomerName());
       // simpleCustomerDTO.setSalesPerson(customer.getSalesRepEmployee().getFirstName()+ ' '+ customer.getSalesRepEmployee().getLastName());
        simpleCustomerDTO.setCity(customer.getCity());
        simpleCustomerDTO.setCountry(customer.getCountry());
        simpleCustomerDTO.setPhone(customer.getPhone());
        return simpleCustomerDTO;


    }
}
