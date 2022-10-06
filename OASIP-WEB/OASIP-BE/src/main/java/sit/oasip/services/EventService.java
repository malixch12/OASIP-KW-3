package sit.oasip.services;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import sit.oasip.Component.JwtTokenUtil;
import sit.oasip.dtos.EventDTOs.AddEventDTO;
import sit.oasip.dtos.EventDTOs.EditEventDTO;
import sit.oasip.dtos.EventDTOs.GetEventDTO;
import sit.oasip.entities.Event;
import sit.oasip.entities.EventCategoryOwner;
import sit.oasip.entities.Eventcategory;
import sit.oasip.entities.User;
import sit.oasip.javainuse.config.JwtRequestFilter;
import sit.oasip.repositories.EventCategoryOwnerRepository;
import sit.oasip.repositories.EventRepository;
import sit.oasip.repositories.EventcategoryRepository;
import sit.oasip.repositories.UserRepository;
import sit.oasip.utils.ListMapper;
import sit.oasip.utils.PageMapper;
import sit.oasip.utils.Role;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;

@Service
public class EventService {
    private final EventcategoryRepository cateRepository;
    private final EventRepository repository;
    private final UserRepository userRepository;
    private final EventCategoryOwnerRepository eventCategoryOwnerRepository;

    @Autowired
    private ListMapper listMapper;
    @Autowired
    private PageMapper pageMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    private HttpServletRequest request;


    @Autowired
    public EventService(EventCategoryOwnerRepository eventCategoryOwnerRepository,UserRepository userRepository, EventRepository repository, EventcategoryRepository cateRepository, HttpServletRequest request) {
        this.repository = repository;
        this.cateRepository = cateRepository;
        this.request = request;
        this.userRepository = userRepository;
        this.eventCategoryOwnerRepository = eventCategoryOwnerRepository;
    }

    long now = (System.currentTimeMillis()) / 1000;
    Instant dateNow = Instant.now().ofEpochSecond(now);

    private List<Event> getEvents(List<Event> events) {
        String token = jwtRequestFilter.extractJwtFromRequest(request);
        String email = jwtTokenUtil.getAllClaimsFromToken(token).getSubject();
        String role = jwtTokenUtil.getAllClaimsFromToken(token).get("role").toString();
        List<Event> event = new ArrayList<>();


        if (role.equals(Role.Student.name())) {
            event = getEventByStudent(email, events);
        } else if (role.equals(Role.Admin.name())) {
            event = events;
        } else if (role.equals(Role.Lecturer.name())) {
            User user = userRepository.findByEmail(email);
            List<EventCategoryOwner> owners = eventCategoryOwnerRepository.findByUserID(user);
            event = getEventByLecturer(owners, events);
        }
        return event;
    }

    private List<Event> getEventByLecturer(List<EventCategoryOwner> owners, List<Event> events) {
        List<Event> eventByEventCateId = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            for (int j = 0; j < owners.size(); j++) {
                if (events.get(i).getEventCategoryID().equals(owners.get(j).getEventCategoryID().getEventCategoryID())) {
                    eventByEventCateId.add(events.get(i));
                    System.out.println(owners.get(j));
                }
            }

        }
        return eventByEventCateId;
    }

    private List<Event> getEventByStudent(String email, List<Event> events) {
        List<Event> eventByEmail = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getBookingEmail().equals(email)) {
                eventByEmail.add(events.get(i));
            }
        }
        return eventByEmail;
    }


    public Page<GetEventDTO> getSimpleEventAll(Pageable pageable) {
        List<Event> allEvent = repository.findAll(Sort.by("eventStartTime").descending());
        List<GetEventDTO> listEventDTO = listMapper
                .mapList(getEvents(allEvent), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public GetEventDTO getSimpleEventById(int id) {
        String token = jwtRequestFilter.extractJwtFromRequest(request);
        Event event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + " Does Not Exist !!!"));
        if (jwtTokenUtil.getAllClaimsFromToken(token).get("role").toString().equals(Role.Student.name())) {
            if (event.getBookingEmail().equals(jwtTokenUtil.getAllClaimsFromToken(token).getSubject()) == false) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "the booking email must be the same as the student's email");
            }
        }
        return modelMapper.map(event, GetEventDTO.class);
    }

    public Page<GetEventDTO> getSimpleEventDate(Instant date, Pageable pageable) {
        List<Event> allEvent = repository.findByEventStartTimeEquals(date, Sort.by("eventStartTime").ascending());
        List<GetEventDTO> listEventDTO = listMapper.mapList(getEvents(allEvent), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<GetEventDTO> getSimpleEventPastDate(Pageable pageable) {
        List<Event> allEvent = repository.findByEventStartTimeLessThan(dateNow, Sort.by("eventStartTime").descending());
        List<GetEventDTO> listEventDTO = listMapper.mapList(getEvents(allEvent), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<GetEventDTO> getSimpleEventFutureDate(Pageable pageable) {
        List<Event> allEvent = repository.findByEventStartTimeGreaterThan(dateNow, Sort.by("eventStartTime").ascending());
        List<GetEventDTO> listEventDTO = listMapper.mapList(getEvents(allEvent), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    // get event by category
    public List<GetEventDTO> getEventAllByCategory(int eventCategoryID) {
        List<Event> allEvent = repository.findByEventCategoryID(eventCategoryID, Sort.by("eventStartTime").descending());
        return listMapper.mapList(getEvents(allEvent), GetEventDTO.class, modelMapper);
    }

    public Page<GetEventDTO> getEventByCategory(int eventCategoryID, Pageable pageable) {
        List<Event> allEvent = repository.findByEventCategoryID(eventCategoryID, Sort.by("eventStartTime").descending());
        List<GetEventDTO> listEventDTO = listMapper.mapList(getEvents(allEvent), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<GetEventDTO> getEventDateByCategory(int eventCategoryID, Instant date, Pageable pageable) {
        List<Event> allEvent = repository.findByEventCategoryIDAndEventStartTimeEquals(eventCategoryID, date);
        List<GetEventDTO> listEventDTO = listMapper.mapList(getEvents(allEvent), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<GetEventDTO> getEventPastDateByCategory(int eventCategoryID, Pageable pageable) {
        List<Event> allEvent = repository.findByEventCategoryIDAndEventStartTimeLessThan(eventCategoryID, dateNow,
                Sort.by("eventStartTime").descending());
        List<GetEventDTO> listEventDTO = listMapper.mapList(getEvents(allEvent), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<GetEventDTO> getEventFutureDateByCategory(int eventCategoryID, Pageable pageable) {
        List<Event> allEvent = repository.findByEventCategoryIDAndEventStartTimeGreaterThan(eventCategoryID, dateNow);
        List<GetEventDTO> listEventDTO = listMapper.mapList(getEvents(allEvent), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public void delete(int eventID) {
        String token = jwtRequestFilter.extractJwtFromRequest(request);
        Event event = repository.findById(eventID).orElseThrow(() -> new RuntimeException(eventID + " Does not exit !!!"));
        checkEmail(event.getBookingEmail(), HttpStatus.FORBIDDEN);
        repository.deleteById(eventID);
    }

    // check overlapping
    public void checkOverlapping(Instant startime, int categoryId) {
        Eventcategory eventcategory = cateRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(categoryId + "Does not exit !!!"));
        List<GetEventDTO> listAll = getEventAllByCategory(categoryId);

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

    private void checkEmail(String email, HttpStatus status) {
        String token = jwtRequestFilter.extractJwtFromRequest(request);
        if (jwtTokenUtil.getAllClaimsFromToken(token).get("role").toString().equals(Role.Student.name())) {
            if (email.equals(jwtTokenUtil.getAllClaimsFromToken(token).getSubject()) == false) {
                throw new ResponseStatusException(status, "the booking email must be the same as the student's email");
            }
        }
    }

    public Event add(AddEventDTO newEvent) throws MessagingException, IOException {
        Eventcategory eventcategory = cateRepository.findById(newEvent.getEventCategoryID())
                .orElseThrow(() -> new RuntimeException(newEvent.getEventCategoryID() + "Does not exit !!!"));

        Event event = new Event();
        checkEmail(newEvent.getBookingEmail(), HttpStatus.BAD_REQUEST);
        checkOverlapping(newEvent.getEventStartTime(), newEvent.getEventCategoryID());

        event.setBookingName(newEvent.getBookingName());
        event.setBookingEmail(newEvent.getBookingEmail());
        event.setEventNotes(newEvent.getEventNotes());
        event.setEventStartTime(newEvent.getEventStartTime());
        event.setEventCategoryID(newEvent.getEventCategoryID());
        event.setEventDuration(eventcategory.getEventDuration());
        event.setEventCategory(eventcategory.getEventCategoryName());
        Event event1 = modelMapper.map(event, Event.class);
        repository.saveAndFlush(event1);
        sendmail(event);
        return event;


    }

    public Event update(EditEventDTO updateEvent, int bookingId) {
        if (updateEvent.getEventStartTime() != null) {
            Event event = repository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Bookind ID " + bookingId + "Does not exit !!!"));
            checkEmail(event.getBookingEmail(), HttpStatus.FORBIDDEN);
            checkOverlapping(updateEvent.getEventStartTime(), event.getEventCategoryID());
        }

        Event event = repository.findById(bookingId).map(e -> {
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
        return modelMapper.map(event, Event.class);
    }

    private void sendmail(Event event) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("oasip.kw3.noreply@gmail.com", "dzszgiijsnafzhlx");
            }
        });
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' hh:mm a").withZone(ZoneId.systemDefault());

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("oasip.kw3.noreply@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(event.getBookingEmail()));
        msg.setSubject("Your booking is complete.");
        msg.setContent("Your booking name : " + event.getBookingName() +
                        "<br> Event category : " + event.getEventCategory() +

                        "<br><br>Start date and time : " + formatter.format(event.getEventStartTime()) +
                        "<br>Event duration : " + event.getEventDuration() +

                        "<br><br>Event note : " + event.getEventNotes()
                , "text/html; charset=utf-8");
        msg.setSentDate(new Date());

        Transport.send(msg);
    }

}