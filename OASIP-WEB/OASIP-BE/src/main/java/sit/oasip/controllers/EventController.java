package sit.oasip.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.multipart.MultipartFile;
import sit.oasip.dtos.EventDTOs.AddEventDTO;
import sit.oasip.dtos.EventDTOs.EditEventDTO;
import sit.oasip.dtos.EventDTOs.GetDateTimeEvent;
import sit.oasip.dtos.EventDTOs.GetEventDTO;
import sit.oasip.entities.Event;
import sit.oasip.repositories.EventRepository;
import sit.oasip.services.EventService;
import org.springframework.data.domain.PageRequest;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventRepository repository;

    @Autowired
    private EventService eventService;

    public EventController(EventRepository repository) {
        this.repository = repository;
    }

    // get date time only (bind important value)
    @GetMapping("/datetime")
    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer','Guest')")
    public List<GetDateTimeEvent> getDateTimeEvent() {
        return eventService.getDateTimeEvents();
    }
    @GetMapping("/datetime/date")
    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer','Guest')")
    public List<GetDateTimeEvent> getDateTimeEventByDate( @RequestParam Instant date) {
        return eventService.getDateTimeEventsbyDate(date);
    }
    @GetMapping("/datetime/past")
    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer','Guest')")
    public List<GetDateTimeEvent> getDateTimeEventByPast() {
        return eventService.getDateTimeEventsbyPastDate();
    }
    @GetMapping("/datetime/future")
    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer','Guest')")
    public List<GetDateTimeEvent> getDateTimeEventByFuture() {
        return eventService.getDateTimeEventsbyFutureDate();
    }

    // get simple event
    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer')")
    @GetMapping("")
    public Page<GetEventDTO> getEventByAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize,
            HttpServletRequest request
    ) {
        return eventService.getSimpleEventAll(PageRequest.of(page, pageSize));
    }

    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer')")
    @GetMapping("/{id}")
    public GetEventDTO getEventById(@PathVariable Integer id) {
        return eventService.getSimpleEventById(id);
    }

    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer')")
    @GetMapping("/dates")
    public Page<GetEventDTO> getEventDate(
            @RequestParam Instant date,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getSimpleEventDate(date, PageRequest.of(page, pageSize));
    }

    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer')")
    @GetMapping("/pastdays")
    public Page<GetEventDTO> getEventPastDate(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getSimpleEventPastDate(PageRequest.of(page, pageSize));
    }

    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer')")
    @GetMapping("/futuredays")
    public Page<GetEventDTO> getEventFutureDate(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getSimpleEventFutureDate(PageRequest.of(page, pageSize));
    }

    // get event by category
    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer')")
    @GetMapping("/categories/{eventCategoryID}")
    public Page<GetEventDTO> getEventByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getEventByCategory(eventCategoryID, PageRequest.of(page, pageSize, Sort.by(sortBy)));
    }

    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer')")
    @GetMapping("/categories/dates/{eventCategoryID}")
    public Page<GetEventDTO> getEventDateByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam Instant date,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getEventDateByCategory(eventCategoryID, date, PageRequest.of(page, pageSize, Sort.by(sortBy)));
    }

    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer')")
    @GetMapping("/categories/pastdays/{eventCategoryID}")
    public Page<GetEventDTO> getEventPastDateByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getEventPastDateByCategory(eventCategoryID, PageRequest.of(page, pageSize, Sort.by(sortBy)));
    }

    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer')")
    @GetMapping("/categories/futuredays/{eventCategoryID}")
    public Page<GetEventDTO> getEventFutureDateByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getEventFutureDateByCategory(eventCategoryID, PageRequest.of(page, pageSize, Sort.by(sortBy)));
    }

    @PreAuthorize("hasAnyAuthority('Admin','Student')")
    @DeleteMapping("/{eventID}")
    public void delete(@PathVariable Integer eventID) throws IOException {
        eventService.delete(eventID);
    }

    @PreAuthorize("hasAnyAuthority('Admin','Student')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Event create(@Validated @ModelAttribute AddEventDTO newEvent) throws MessagingException, IOException {
        return eventService.add(newEvent);
    }

    @PreAuthorize("hasAnyAuthority('Admin','Student')")
    @PutMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public Event update(@Valid @ModelAttribute EditEventDTO updateEvent, @PathVariable Integer bookingId) {
        return eventService.update(updateEvent, bookingId);
    }
}
