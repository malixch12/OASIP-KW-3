package sit.int204.classicmodelsservice2.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.classicmodelsservice2.dtos.SimpleEventDTO;
import sit.int204.classicmodelsservice2.entities.Event;
import sit.int204.classicmodelsservice2.repositories.EventRepository;

@Service
public class EventService {

    private final EventRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public SimpleEventDTO getSimpleEventById(Integer id) {
        Event event = repository.findById(id).orElseThrow(()
                ->new ResponseStatusException(HttpStatus.NOT_FOUND,  id +" Does Not Exist !!!" ));

        return modelMapper.map(event,SimpleEventDTO.class);
    
    }
  
    public List<Event> getEventByCatetory(Integer eventCategoryID) {
        return repository.findByEventCategoryID(eventCategoryID);
    }

    
    public List<Event> getSimpleEventAll() {
        List<Event> event =  repository.findAll();
;
        return event;
    
    }
}
