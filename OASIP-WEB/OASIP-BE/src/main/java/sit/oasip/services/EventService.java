package sit.oasip.services;

import java.time.Instant;
import java.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import sit.oasip.dtos.SimpleEventDTO;
import sit.oasip.entities.Event;
import sit.oasip.entities.Eventcategory;
import sit.oasip.repositories.EventRepository;
import sit.oasip.repositories.EventcategoryRepository;
import sit.oasip.utils.ListMapper;
import sit.oasip.utils.PageMapper;

@Service
public class EventService {
    private final EventcategoryRepository cateRepository;
    private final EventRepository repository;
    private ListMapper listMapper = new ListMapper();
    private PageMapper pageMapper = new PageMapper();

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public EventService(EventRepository repository, EventcategoryRepository cateRepository) {
        this.repository = repository;
        this.cateRepository = cateRepository;
    }

    long now = (System.currentTimeMillis()) / 1000;
    Instant dateNow = Instant.now().ofEpochSecond(now);



    // get event
    public List<SimpleEventDTO> getEventAll() {
        return listMapper.mapList(repository.findAll(), SimpleEventDTO.class, modelMapper);
    }

    public Page<SimpleEventDTO> getSimpleEventAll(Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper
                .mapList(repository.findAll(Sort.by("eventStartTime").descending()), SimpleEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public SimpleEventDTO getSimpleEventById(int id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + " Does Not Exist !!!"));
        return modelMapper.map(event, SimpleEventDTO.class);
    }

    public Page<SimpleEventDTO> getSimpleEventDate(Instant date, Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(
                repository.findByEventStartTimeEquals(date, Sort.by("eventStartTime").ascending()),
                SimpleEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<SimpleEventDTO> getSimpleEventPastDate(Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(
                repository.findByEventStartTimeLessThan(dateNow, Sort.by("eventStartTime").descending()),
                SimpleEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<SimpleEventDTO> getSimpleEventFutureDate(Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(
                repository.findByEventStartTimeGreaterThan(dateNow, Sort.by("eventStartTime").ascending()),
                SimpleEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    // get event by category
    public List<SimpleEventDTO> getEventAllByCategory(int eventCategoryID) {
        return listMapper.mapList(
                repository.findByEventCategoryID(eventCategoryID, Sort.by("eventStartTime").descending()),
                SimpleEventDTO.class, modelMapper);
    }

    public Page<SimpleEventDTO> getEventByCategory(int eventCategoryID, Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(
                repository.findByEventCategoryID(eventCategoryID, Sort.by("eventStartTime").descending()),
                SimpleEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<SimpleEventDTO> getEventDateByCategory(int eventCategoryID, Instant date, Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(
                repository.findByEventCategoryIDAndEventStartTimeEquals(eventCategoryID, date),
                SimpleEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<SimpleEventDTO> getEventPastDateByCategory(int eventCategoryID, Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(
                repository.findByEventCategoryIDAndEventStartTimeLessThan(eventCategoryID, dateNow,
                        Sort.by("eventStartTime").descending()),
                SimpleEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<SimpleEventDTO> getEventFutureDateByCategory(int eventCategoryID, Pageable pageable) {
        List<SimpleEventDTO> listEventDTO = listMapper.mapList(
                repository.findByEventCategoryIDAndEventStartTimeGreaterThan(eventCategoryID, dateNow),
                SimpleEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public void delete(int eventID) {
        repository.findById(eventID).orElseThrow(()-> new RuntimeException(eventID + "Does not exit !!!"));
        repository.deleteById(eventID);
    }

    // check overlapping
    public void checkOverlapping(Instant startime, int categoryId) {
        Eventcategory eventcategory = cateRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(categoryId + "Does not exit !!!"));
        List<SimpleEventDTO> listAll = getEventAllByCategory(categoryId);

        Instant newEventStartTime = startime;
        Instant newEventEndTime = startime.plusSeconds(eventcategory.getEventDuration() * 60);
        for (int i = 0; i < listAll.size(); i++) {
            Instant startTime = listAll.get(i).getEventStartTime();
            Instant endTime = listAll.get(i).getEventStartTime().plusSeconds(listAll.get(i).getEventDuration() * 60);

            if (newEventStartTime.isBefore(startTime) && newEventEndTime.isAfter(startTime) ||
                    newEventStartTime.isBefore(endTime) && newEventEndTime.isAfter(endTime) ||
                    newEventStartTime.isBefore(startTime) && newEventEndTime.isAfter(endTime) ||
                    newEventStartTime.isAfter(startTime) && newEventEndTime.isBefore(endTime)
                    || newEventStartTime.equals(startTime)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time is overlapping");
            }
        }
    }

    public SimpleEventDTO add(Event newEvent) {
        Eventcategory eventcategory = cateRepository.findById(newEvent.getEventCategoryID())
                .orElseThrow(() -> new RuntimeException(newEvent.getEventCategoryID() + "Does not exit !!!"));

        checkOverlapping(newEvent.getEventStartTime(), newEvent.getEventCategoryID());

        newEvent.setEventDuration(eventcategory.getEventDuration());
        newEvent.setEventCategory(eventcategory.getEventCategoryName());
        repository.saveAndFlush(newEvent);
        return modelMapper.map(newEvent, SimpleEventDTO.class);
    }

    public SimpleEventDTO update(SimpleEventDTO updateEvent, int BookingId) {
        if (updateEvent.getEventStartTime() != null) {
            Event event = repository.findById(BookingId)
                    .orElseThrow(() -> new RuntimeException(BookingId + "Does not exit !!!"));

            checkOverlapping(updateEvent.getEventStartTime(), event.getEventCategoryID());
        }

        Event event = repository.findById(BookingId).map(e -> {
            if (updateEvent.getEventStartTime() != null && updateEvent.getEventNotes() != null) {
                e.setEventStartTime(updateEvent.getEventStartTime());
                e.setEventNotes(updateEvent.getEventNotes());
            } else if (updateEvent.getEventNotes() != null) {
                e.setEventStartTime(e.getEventStartTime());
                e.setEventNotes(updateEvent.getEventNotes());
            } else if (updateEvent.getEventStartTime() != null) {
                e.setEventStartTime(updateEvent.getEventStartTime());
            }
            return repository.saveAndFlush(e);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "test"));
        return modelMapper.map(event, SimpleEventDTO.class);
    }

}