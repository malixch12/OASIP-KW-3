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
        return eventService.getSimpleEventAll(PageRequest.of(page, pageSize),request);
    }

    @GetMapping("/{id}")
    public GetEventDTO getEventById(@PathVariable Integer id,HttpServletRequest request) {
        return eventService.getSimpleEventById(id,request);
    }

    @GetMapping("/dates")
    public Page<GetEventDTO> getEventDate(
            @RequestParam Instant date,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize,HttpServletRequest request) {
        return eventService.getSimpleEventDate(date,PageRequest.of(page, pageSize),request);
    }

    @GetMapping("/pastdays")
    public Page<GetEventDTO> getEventPastDate(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize,HttpServletRequest request) {
        return eventService.getSimpleEventPastDate(PageRequest.of(page, pageSize),request);
    }

    @GetMapping("/futuredays")
    public Page<GetEventDTO> getEventFutureDate(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize,HttpServletRequest request) {
        return eventService.getSimpleEventFutureDate(PageRequest.of(page, pageSize),request);
    }

    // get event by category
    @GetMapping("/categories/{eventCategoryID}")
    public Page<GetEventDTO> getEventByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize,HttpServletRequest request) {
                return eventService.getEventByCategory(eventCategoryID,PageRequest.of(page, pageSize,  Sort.by(sortBy)),request);
    }

    @GetMapping("/categories/dates/{eventCategoryID}")
    public Page<GetEventDTO> getEventDateByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam Instant date,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize,HttpServletRequest request) {
        return eventService.getEventDateByCategory(eventCategoryID, date, PageRequest.of(page, pageSize,  Sort.by(sortBy)),request);
    }

    @GetMapping("/categories/pastdays/{eventCategoryID}")
    public Page<GetEventDTO> getEventPastDateByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize,HttpServletRequest request) {
      return eventService.getEventPastDateByCategory(eventCategoryID, PageRequest.of(page, pageSize,  Sort.by(sortBy)),request);
    }

    @GetMapping("/categories/futuredays/{eventCategoryID}")
    public Page<GetEventDTO> getEventFutureDateByCategory(
            @PathVariable Integer eventCategoryID,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize,HttpServletRequest request) {
        return eventService.getEventFutureDateByCategory(eventCategoryID, PageRequest.of(page, pageSize,  Sort.by(sortBy)),request);
    }

    @DeleteMapping("/{eventID}")
    public void delete(@PathVariable Integer eventID,HttpServletRequest request) {
        eventService.delete(eventID,request);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Event create(@Validated @RequestBody AddEventDTO newEvent, HttpServletRequest request) throws MessagingException, IOException {
        return eventService.add(newEvent,request);
    }

    @PutMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public Event update(@Validated @RequestBody EditEventDTO updateEvent, @PathVariable Integer bookingId,HttpServletRequest request) {
        return eventService.update(updateEvent, bookingId,request);
    }

}
