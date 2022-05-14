package sit.int204.classicmodelsservice2.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import sit.int204.classicmodelsservice2.dtos.SimpleEventDTO;
import sit.int204.classicmodelsservice2.entities.Event;
import sit.int204.classicmodelsservice2.repositories.EventRepository;
import sit.int204.classicmodelsservice2.services.EventService;
import org.springframework.data.domain.PageRequest;
import java.util.List;

@RestController

@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private final EventRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public EventController(EventRepository repository) {
        this.repository = repository;
    }

    // @GetMapping("")
    // public List<SimpleEventDTO> getEventByAll( @RequestParam(defaultValue = "eventStartTime") String sortBy,
    //         @RequestParam(defaultValue = "0") Integer page,
    //         @RequestParam(defaultValue = "8") Integer pageSize) {
    // return eventService.getSimpleEventAll(sortBy, page, pageSize);
    // }

    @GetMapping("")
    public Page<Event> getEventByAll(
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        Sort sort = Sort.by(sortBy);
        return repository.findAll(PageRequest.of(page, pageSize, sort));
    }

    @GetMapping("/{id}")
    public SimpleEventDTO getEventById(@PathVariable Integer id) {
        return eventService.getSimpleEventById(id);
    }

    @GetMapping("/category/{eventCategoryID}")
    public List<SimpleEventDTO> getEventByCatetory(@PathVariable Integer
    eventCategoryID) {
    return eventService.getEventByCatetory(eventCategoryID);
    }

    // @GetMapping("/category/{eventCategoryID}")
    // public Page<Event> getEventByCategory(
    //         @PathVariable Integer eventCategoryID,
    //         @RequestParam(defaultValue = "eventStartTime") String sortBy,
    //         @RequestParam(defaultValue = "0") Integer page,
    //         @RequestParam(defaultValue = "2") Integer pageSize) {
    //         Sort sort = Sort.by(sortBy);
    //     return repository.findByEventCategoryID(eventCategoryID,PageRequest.of(page,pageSize,sort));
    // }

    @DeleteMapping("/{eventID}")
    public void delete(@PathVariable Integer eventID) {
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
