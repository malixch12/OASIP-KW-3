package sit.oasip.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import sit.oasip.dtos.EventDTOs.AddEventDTO;
import sit.oasip.dtos.EventDTOs.EditEventDTO;
import sit.oasip.dtos.EventDTOs.GetEventDTO;
import sit.oasip.entities.Event;
import sit.oasip.services.EventService;
import org.springframework.data.domain.PageRequest;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("")
    public Page<GetEventDTO> getEventByAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize,
            HttpServletRequest request
    ) {
        return eventService.getSimpleEventAll(PageRequest.of(page, pageSize));
    }

    @GetMapping("/{id}")
    public GetEventDTO getEventById(@PathVariable Integer id) {
        return eventService.getSimpleEventById(id);
    }

    @GetMapping("/dates")
    public Page<GetEventDTO> getEventDate(
            @RequestParam Instant date,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getSimpleEventDate(date,PageRequest.of(page, pageSize));
    }

    @GetMapping("/pastdays")
    public Page<GetEventDTO> getEventPastDate(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getSimpleEventPastDate(PageRequest.of(page, pageSize));
    }

    @GetMapping("/futuredays")
    public Page<GetEventDTO> getEventFutureDate(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getSimpleEventFutureDate(PageRequest.of(page, pageSize));
    }

    // get event by category
    @GetMapping("/categories/{eventCategoryID}")
    public Page<GetEventDTO> getEventByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
                return eventService.getEventByCategory(eventCategoryID,PageRequest.of(page, pageSize,  Sort.by(sortBy)));
    }

    @GetMapping("/categories/dates/{eventCategoryID}")
    public Page<GetEventDTO> getEventDateByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam Instant date,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return eventService.getEventDateByCategory(eventCategoryID, date, PageRequest.of(page, pageSize,  Sort.by(sortBy)));
    }

    @GetMapping("/categories/pastdays/{eventCategoryID}")
    public Page<GetEventDTO> getEventPastDateByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
      return eventService.getEventPastDateByCategory(eventCategoryID, PageRequest.of(page, pageSize,  Sort.by(sortBy)));
    }

    @GetMapping("/categories/futuredays/{eventCategoryID}")
    public Page<GetEventDTO> getEventFutureDateByCategory(
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
    public Event create(@Validated @RequestBody AddEventDTO newEvent) throws MessagingException, IOException {
        return eventService.add(newEvent);
    }

    @PutMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public Event update(@Validated @RequestBody EditEventDTO updateEvent, @PathVariable Integer bookingId) {
        return eventService.update(updateEvent, bookingId);
    }

}
