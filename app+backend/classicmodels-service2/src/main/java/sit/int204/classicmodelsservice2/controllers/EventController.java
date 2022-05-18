package sit.int204.classicmodelsservice2.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import org.springframework.http.ResponseEntity;

import sit.int204.classicmodelsservice2.dtos.SimpleEventDTO;
import sit.int204.classicmodelsservice2.entities.Event;
import sit.int204.classicmodelsservice2.repositories.EventRepository;
import sit.int204.classicmodelsservice2.services.EventService;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

    // get all
    @GetMapping("/get-all")
    public List<SimpleEventDTO> getEventNoPage() {
        return eventService.getEventAll();
    }

    @GetMapping("")
    public Page<SimpleEventDTO> getEventByAll(
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        Sort sort = Sort.by(sortBy);
        return eventService.getSimpleEventAll(PageRequest.of(page, pageSize, sort));
    }

    @GetMapping("/{id}")
    public SimpleEventDTO getEventById(@PathVariable Integer id) {
        return eventService.getSimpleEventById(id);
    }

    @GetMapping("/date")
    public Page<SimpleEventDTO> getEventDate(
            @RequestParam Instant date,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getSimpleEventDate(date, PageRequest.of(page, pageSize, Sort.by(sortBy)));
    }

    @GetMapping("/past")
    public Page<SimpleEventDTO> getEventPastDate(
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getSimpleEventPastDate(PageRequest.of(page, pageSize, Sort.by(sortBy)));
    }

    @GetMapping("/future")
    public Page<SimpleEventDTO> getEventFutureDate(
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getSimpleEventFutureDate(PageRequest.of(page, pageSize, Sort.by(sortBy)));
    }

    // get event by category
    @GetMapping("/category/{eventCategoryID}")
    public Page<SimpleEventDTO> getEventByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getEventByCatetory(eventCategoryID, PageRequest.of(page, pageSize, Sort.by(sortBy)));
    }

    @GetMapping("/category/date/{eventCategoryID}")
    public Page<SimpleEventDTO> getEventDateByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam Instant date,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getEventDateByCatetory(eventCategoryID, date,
                PageRequest.of(page, pageSize, Sort.by(sortBy)));
    }

    @GetMapping("/category/past/{eventCategoryID}")
    public Page<SimpleEventDTO> getEventPastDateByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getEventPastDateByCategory(eventCategoryID,
                PageRequest.of(page, pageSize, Sort.by(sortBy)));
    }

    @GetMapping("/category/future/{eventCategoryID}")
    public Page<SimpleEventDTO> getEventFutureDateByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getEventFutureDateByCategory(eventCategoryID,
                PageRequest.of(page, pageSize, Sort.by(sortBy)));
    }

    @DeleteMapping("/{eventID}")
    public void delete(@PathVariable Integer eventID) {
        eventService.delete(eventID);
    }

    // @PostMapping("")
    // // @ResponseStatus(HttpStatus.CREATED)
    // public ResponseEntity<Event> create(@Valid @RequestBody SimpleEventDTO
    // newEvent) {
    // Event event = eventService.save(newEvent);
    // return new ResponseEntity<Event>(event, HttpStatus.CREATED);
    // }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Event create(@RequestBody SimpleEventDTO newEvent) {
        Event event = eventService.save(newEvent);
        return event;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @PutMapping("/{BookingId}")
    public Event update(@RequestBody Event updateEvent, @PathVariable Integer BookingId) {
        return eventService.update(updateEvent, BookingId);
    }

}
