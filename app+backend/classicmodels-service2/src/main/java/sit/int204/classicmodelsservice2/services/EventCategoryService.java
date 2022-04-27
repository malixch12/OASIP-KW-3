package sit.int204.classicmodelsservice2.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.classicmodelsservice2.dtos.SimpleEventcategoryDTO;
import sit.int204.classicmodelsservice2.entities.Eventcategory;
import sit.int204.classicmodelsservice2.repositories.EventcategoryRepository;

@Service
public class EventCategoryService {

    private final EventcategoryRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public EventCategoryService(EventcategoryRepository repository) {
        this.repository = repository;
    }

    public SimpleEventcategoryDTO getSimpleEventcategoryById(Integer id) {
        Eventcategory eventcategory =  repository.findById(id).orElseThrow(()
                ->new ResponseStatusException(HttpStatus.NOT_FOUND,  id +" Does Not Exist !!!" ));

        return modelMapper.map(eventcategory,SimpleEventcategoryDTO.class);
    
    }
  
    
    public List<Eventcategory> getSimpleEventcategoryAll() {
        List<Eventcategory> eventcategory =  repository.findAll();

        return eventcategory;
    
    }
}
