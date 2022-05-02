package sit.int204.classicmodelsservice2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import sit.int204.classicmodelsservice2.dtos.SimpleEventDTO;
import sit.int204.classicmodelsservice2.entities.Event;
import sit.int204.classicmodelsservice2.repositories.EventRepository;
import sit.int204.classicmodelsservice2.services.EventService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService EventService;

    private final EventRepository repository ;

    @Autowired
    public EventController(EventRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<Event> getEventByAll() {
        return EventService.getSimpleEventAll();
    }
    
    @GetMapping("/{id}")
    public SimpleEventDTO getEventById(@PathVariable Integer id) {
        return EventService.getSimpleEventById(id);
    }

    @GetMapping("/category/{eventCategoryID}")
    public List<Event> getEventByCatetory(@PathVariable Integer eventCategoryID) {
        return EventService.getEventByCatetory(eventCategoryID);
    }
    
    @DeleteMapping("/{eventID}")
    public void delete(@PathVariable Integer eventID){
        EventService.delete(eventID);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Event create(@RequestBody SimpleEventDTO newEvent) {
        return EventService.save(newEvent);
    }

    @PutMapping("/{BookingId}")
    public Event update(@RequestBody Event updateEvent, @PathVariable Integer BookingId) {
        return EventService.update(updateEvent, BookingId);
    }

}
