package sit.int204.classicmodelsservice2.services;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;

import sit.int204.classicmodelsservice2.dtos.SimpleEventDTO;
import sit.int204.classicmodelsservice2.dtos.SimpleEventcategoryDTO;
import sit.int204.classicmodelsservice2.entities.Event;
import sit.int204.classicmodelsservice2.entities.Eventcategory;
import sit.int204.classicmodelsservice2.repositories.EventRepository;
import sit.int204.classicmodelsservice2.repositories.EventcategoryRepository;
import sit.int204.classicmodelsservice2.utils.ListMapper;

@Service
public class EventService {
    private final EventcategoryRepository cateRepository;
    private final EventRepository repository;
    private ListMapper listMapper = new ListMapper();

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public EventService(EventRepository repository, EventcategoryRepository cateRepository) {
        this.repository = repository;
        this.cateRepository = cateRepository;
    }

    long now = (System.currentTimeMillis()) / 1000;
    Instant dateNow = Instant.now().ofEpochSecond(now);

    // list -> page
    public Page<SimpleEventDTO> getPage(Pageable pageable, List<SimpleEventDTO> listEventDTO) {
        int start = (int) pageable.getOffset();
        int end = (int) ((start + pageable.getPageSize()) > listEventDTO.size() ? listEventDTO.size()
                : (start + pageable.getPageSize()));
        Page<SimpleEventDTO> pageEvent = new PageImpl<SimpleEventDTO>(listEventDTO.subList(start, end), pageable,
                listEventDTO.size());
        return pageEvent;
    }

    // get event
    public List<SimpleEventDTO> getEventAll() {
        return listMapper.mapList(repository.findAll(), SimpleEventDTO.class, modelMapper);
    }

    public Page<SimpleEventDTO> getSimpleEventAll(Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(repository.findAll(), SimpleEventDTO.class, modelMapper);
        return getPage(pageable, listEventDTO);
    }

    public SimpleEventDTO getSimpleEventById(Integer id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + " Does Not Exist !!!"));
        return modelMapper.map(event, SimpleEventDTO.class);
    }

    public Page<SimpleEventDTO> getSimpleEventDate(Instant date, Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(repository.findByEventStartTimeEquals(date),
                SimpleEventDTO.class, modelMapper);
        return getPage(pageable, listEventDTO);
    }

    public Page<SimpleEventDTO> getSimpleEventPastDate(Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(repository.findByEventStartTimeLessThan(dateNow),
                SimpleEventDTO.class, modelMapper);
        return getPage(pageable, listEventDTO);
    }

    public Page<SimpleEventDTO> getSimpleEventFutureDate(Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(repository.findByEventStartTimeGreaterThan(dateNow),
                SimpleEventDTO.class, modelMapper);
        return getPage(pageable, listEventDTO);
    }

    // get event by category
    public Page<SimpleEventDTO> getEventByCatetory(Integer eventCategoryID, Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(repository.findByEventCategoryID(eventCategoryID),
                SimpleEventDTO.class, modelMapper);
        return getPage(pageable, listEventDTO);
    }

    public Page<SimpleEventDTO> getEventDateByCatetory(Integer eventCategoryID, Instant date, Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(
                repository.findByEventCategoryIDAndEventStartTimeEquals(eventCategoryID, date),
                SimpleEventDTO.class, modelMapper);
        return getPage(pageable, listEventDTO);
    }

    public Page<SimpleEventDTO> getEventPastDateByCategory(Integer eventCategoryID, Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(
                repository.findByEventCategoryIDAndEventStartTimeLessThan(eventCategoryID, dateNow),
                SimpleEventDTO.class, modelMapper);
        return getPage(pageable, listEventDTO);
    }

    public Page<SimpleEventDTO> getEventFutureDateByCategory(Integer eventCategoryID, Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(
                repository.findByEventCategoryIDAndEventStartTimeGreaterThan(eventCategoryID, dateNow),
                SimpleEventDTO.class, modelMapper);
        return getPage(pageable, listEventDTO);
    }

    public void delete(Integer eventID) {
        repository.findById(eventID).orElseThrow(() -> new RuntimeException(eventID + "Does not exit !!!"));
        repository.deleteById(eventID);
    }

    public SimpleEventDTO add(Event newEvent) {
        Event event = new Event();
        Eventcategory eventcategory = cateRepository.findById(newEvent.getEventCategoryID())
                .orElseThrow(() -> new RuntimeException(newEvent.getEventCategoryID() + "Does not exit !!!"));
        newEvent.setEventDuration(eventcategory.getEventDuration());
        newEvent.setEventCategory(eventcategory.getEventCategoryName());
        event = modelMapper.map(newEvent, Event.class);
        repository.saveAndFlush(newEvent);
        return modelMapper.map(event, SimpleEventDTO.class);
    }

    public SimpleEventDTO update(SimpleEventDTO updateEvent, Integer BookingId) {
        Event event = repository.findById(BookingId).map(e -> {
            if (updateEvent.getEventStartTime() != null && updateEvent.getEventNotes() != null) {
                e.setEventStartTime(updateEvent.getEventStartTime());
                e.setEventNotes(updateEvent.getEventNotes());
                return repository.saveAndFlush(e);
            } else if (updateEvent.getEventNotes() != null) {
                e.setEventStartTime(e.getEventStartTime());
                e.setEventNotes(updateEvent.getEventNotes());
                return repository.saveAndFlush(e);
            } else if (updateEvent.getEventStartTime() != null) {
                e.setEventStartTime(updateEvent.getEventStartTime());
                return repository.saveAndFlush(e);
            } else {
                return repository.saveAndFlush(e);
            }
        }).orElseThrow(() -> new AssertionError("Can not update !!!"));

        return modelMapper.map(event, SimpleEventDTO.class);
    }
}
