package sit.int204.classicmodelsservice2.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.classicmodelsservice2.dtos.SimpleEventDTO;
import sit.int204.classicmodelsservice2.dtos.SimpleEventcategoryDTO;
import sit.int204.classicmodelsservice2.entities.Eventcategory;
import sit.int204.classicmodelsservice2.repositories.EventcategoryRepository;
import sit.int204.classicmodelsservice2.utils.ListMapper;

@Service
public class EventCategoryService {

    private final EventcategoryRepository repository;

    private ListMapper listMapper = new ListMapper();
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    public EventCategoryService(EventcategoryRepository repository) {
        this.repository = repository;
    }


    public List<SimpleEventcategoryDTO> getSimpleEventcategoryAll() {
        return listMapper.mapList(repository.findAll(), SimpleEventcategoryDTO.class,modelMapper);
    }

    public SimpleEventcategoryDTO getSimpleEventcategoryById(Integer id) {
        Eventcategory eventcategory =  repository.findById(id).orElseThrow(()
                ->new ResponseStatusException(HttpStatus.NOT_FOUND,  id +" Does Not Exist !!!" ));

        return modelMapper.map(eventcategory,SimpleEventcategoryDTO.class);
    
    }
}
