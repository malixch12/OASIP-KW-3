package sit.int204.classicmodelsservice2.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.http.HttpStatus;
import sit.int204.classicmodelsservice2.dtos.SimpleEventDTO;
import sit.int204.classicmodelsservice2.entities.Event;
import sit.int204.classicmodelsservice2.repositories.EventRepository;
import sit.int204.classicmodelsservice2.services.EventService;
import sit.int204.classicmodelsservice2.utils.ListMapper;
import java.util.List;

@Configuration
@EnableWebMvc
@RestController

@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private final EventRepository repository ;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    public EventController(EventRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<SimpleEventDTO> getEventByAll() {
        return eventService.getSimpleEventAll();
    }

    
    @GetMapping("/{id}")
    public SimpleEventDTO getEventById(@PathVariable Integer id) {
        return eventService.getSimpleEventById(id);
    }

    @GetMapping("/category/{eventCategoryID}")
    public List<SimpleEventDTO> getEventByCatetory(@PathVariable Integer eventCategoryID) {
        return eventService.getEventByCatetory(eventCategoryID);
    }
    
    @DeleteMapping("/{eventID}")
    public void delete(@PathVariable Integer eventID){
        eventService.delete(eventID);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Event create(@RequestBody SimpleEventDTO newEvent) {
        return eventService.save(newEvent);
    }

    @PutMapping("/{BookingId}")
    public Event update(@RequestBody Event updateEvent, @PathVariable Integer BookingId) {
        return eventService.update(updateEvent, BookingId);
    }
   
}
