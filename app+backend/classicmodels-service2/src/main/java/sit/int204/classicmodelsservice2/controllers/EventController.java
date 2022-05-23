package sit.int204.classicmodelsservice2.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import org.springframework.validation.annotation.Validated;

import sit.int204.classicmodelsservice2.dtos.SimpleEventDTO;
import sit.int204.classicmodelsservice2.entities.Event;
import sit.int204.classicmodelsservice2.repositories.EventRepository;
import sit.int204.classicmodelsservice2.services.EventService;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.List;

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
    @GetMapping("")
    public Page<SimpleEventDTO> getEventByAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getSimpleEventAll(PageRequest.of(page, pageSize));
    }

    @GetMapping("/{id}")
    public SimpleEventDTO getEventById(@PathVariable Integer id) {
        return eventService.getSimpleEventById(id);
    }

    @GetMapping("/date")
    public Page<SimpleEventDTO> getEventDate(
            @RequestParam Instant date,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getSimpleEventDate(date,PageRequest.of(page, pageSize));
    }

    @GetMapping("/past")
    public Page<SimpleEventDTO> getEventPastDate(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getSimpleEventPastDate(PageRequest.of(page, pageSize));
    }

    @GetMapping("/future")
    public Page<SimpleEventDTO> getEventFutureDate(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getSimpleEventFutureDate(PageRequest.of(page, pageSize));
    }

    // get event by category
    @GetMapping("/category/{eventCategoryID}")
    public Page<SimpleEventDTO> getEventByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
                return eventService.getEventByCatetory(eventCategoryID,PageRequest.of(page, pageSize,  Sort.by(sortBy)));
    }

    @GetMapping("/category/date/{eventCategoryID}")
    public Page<SimpleEventDTO> getEventDateByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam Instant date,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getEventDateByCatetory(eventCategoryID, date, PageRequest.of(page, pageSize,  Sort.by(sortBy)));
    }

    @GetMapping("/category/past/{eventCategoryID}")
    public Page<SimpleEventDTO> getEventPastDateByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
      return eventService.getEventPastDateByCategory(eventCategoryID, PageRequest.of(page, pageSize,  Sort.by(sortBy)));
    }

    @GetMapping("/category/future/{eventCategoryID}")
    public Page<SimpleEventDTO> getEventFutureDateByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getEventFutureDateByCategory(eventCategoryID, PageRequest.of(page, pageSize,  Sort.by(sortBy)));
    }

    @DeleteMapping("/{eventID}")
    public void delete(@PathVariable Integer eventID) {
        eventService.delete(eventID);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public SimpleEventDTO create(@Validated @RequestBody Event newEvent) {
        return eventService.add(newEvent);
    }

    @PutMapping("/{BookingId}")
    @ResponseStatus(HttpStatus.OK)
    public SimpleEventDTO update(@Validated @RequestBody SimpleEventDTO updateEvent, @PathVariable Integer BookingId) {
        return eventService.update(updateEvent, BookingId);
    }

}
