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
import sit.int204.classicmodelsservice2.utils.ListMapper;

@Service
public class EventService {

    private final EventRepository repository;

    private ListMapper listMapper = new ListMapper();
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public List<SimpleEventDTO> getSimpleEventAll() {
        return listMapper.mapList(repository.findAll(), SimpleEventDTO.class,modelMapper);
    }

    public SimpleEventDTO getSimpleEventById(Integer id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + " Does Not Exist !!!"));

        return modelMapper.map(event, SimpleEventDTO.class);

    }

    public List<SimpleEventDTO> getEventByCatetory(Integer eventCategoryID ) {
        return listMapper.mapList(repository.findByEventCategoryID(eventCategoryID),SimpleEventDTO.class,modelMapper);
    }

    public void delete(Integer eventID) {
        repository.findById(eventID).orElseThrow(() -> new RuntimeException(eventID + "Does not exit !!!"));
        repository.deleteById(eventID);
    }


    public Event save(SimpleEventDTO newEvent) {
        Event e = modelMapper.map(newEvent, Event.class);
        return repository.saveAndFlush(e);
    }

    public Event update(Event updateEvent, Integer BookingId) {
        Event event = repository.findById(BookingId).map(o -> mapEvent(o, updateEvent))
                .orElseGet(() -> {
                    updateEvent.setBookingId(BookingId);
                    return updateEvent;
                });
        return repository.saveAndFlush(event);
    }

    private Event mapEvent(Event existingEvent, Event updateEvent) {
        existingEvent.setBookingId(updateEvent.getBookingId());
        existingEvent.setBookingName(updateEvent.getBookingName());
        existingEvent.setBookingEmail(updateEvent.getBookingEmail());
        existingEvent.setEventCategory(updateEvent.getEventCategory());
        existingEvent.setEventDuration(updateEvent.getEventDuration());
        existingEvent.setEventNotes(updateEvent.getEventNotes());
        existingEvent.setEventCategoryID(updateEvent.getEventCategoryID());
        existingEvent.setEventStartTime(updateEvent.getEventStartTime());
        return existingEvent;
    }
}
