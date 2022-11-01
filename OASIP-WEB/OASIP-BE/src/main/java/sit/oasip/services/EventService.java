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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import sit.oasip.Component.JwtTokenUtil;
import sit.oasip.dtos.EventDTOs.AddEventDTO;
import sit.oasip.dtos.EventDTOs.EditEventDTO;
import sit.oasip.dtos.EventDTOs.GetEventDTO;
import sit.oasip.entities.Event;
import sit.oasip.entities.Eventcategory;
import sit.oasip.entities.User;
import sit.oasip.config.JwtRequestFilter;
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

    private final HttpServletRequest request;


    @Autowired
    public EventService(EventCategoryOwnerRepository eventCategoryOwnerRepository, UserRepository userRepository, EventRepository repository, EventcategoryRepository cateRepository, HttpServletRequest request) {
        this.repository = repository;
        this.cateRepository = cateRepository;
        this.request = request;
        this.userRepository = userRepository;
        this.eventCategoryOwnerRepository = eventCategoryOwnerRepository;
    }

    long now = (System.currentTimeMillis()) / 1000;
    Instant dateNow = Instant.now().ofEpochSecond(now);

    private List<Event> getEvents(Sort sort, Instant myDate, Integer cateId, String filter) {

        List<Event> event = new ArrayList<>();

        String token = jwtRequestFilter.extractJwtFromRequest(request);
        String email = jwtTokenUtil.getAllClaimsFromToken(token).getSubject();
        String role = jwtTokenUtil.getAllClaimsFromToken(token).get("role").toString();
        if (role.equals(Role.Student.name())) {
            if (filter == null) event = repository.findAllEventByStudent(email, sort);
            else if (filter.equals("date"))
                event = repository.findAllEventByStudentStartTimeEquals(email, sort, myDate);
            else if (filter.equals("past"))
                event = repository.findAllEventByStudentStartTimeLessThan(email, sort, myDate);
            else if (filter.equals("future"))
                event = repository.findAllEventByStudentStartTimeGreaterThan(email, sort, myDate);

            else if (filter.equals("cateId"))
                event = repository.findAllEventByStudentCategoryId(email, cateId, sort);
            else if (filter.equals("catIdDate"))
                event = repository.findAllEventByStudentCategoryIdAndEventStartTimeEquals(email, cateId, sort, myDate);
            else if (filter.equals("cateIdPast"))
                event = repository.findAllEventByStudentCategoryIdAndEventStartTimeLessThan(email, cateId, sort, myDate);
            else if (filter.equals("cateIdFuture"))
                event = repository.findAllEventByStudentCategoryIdAndEventStartTimeGreaterThan(email, cateId, sort, myDate);


        } else if (role.equals(Role.Admin.name())) {
            if (filter == null) event = repository.findAll(sort);
            else if (filter.equals("date"))
                event = repository.findByEventStartTimeEquals(myDate, sort);
            else if (filter.equals("past"))
                event = repository.findByEventStartTimeLessThan(myDate, sort);
            else if (filter.equals("future"))
                event = repository.findByEventStartTimeGreaterThan(myDate, sort);

            else if (filter.equals("cateId"))
                event = repository.findByEventCategoryID(cateId, sort);
            else if (filter.equals("catIdDate"))
                event = repository.findByEventCategoryIDAndEventStartTimeEquals(cateId, myDate);
            else if (filter.equals("cateIdPast"))
                event = repository.findByEventCategoryIDAndEventStartTimeLessThan(cateId, myDate, sort);
            else if (filter.equals("cateIdFuture"))
                event = repository.findByEventCategoryIDAndEventStartTimeGreaterThan(cateId, myDate, sort);


        } else if (role.equals(Role.Lecturer.name())) {
            User user = userRepository.findByEmail(email);
            if (filter == null) event = repository.findAllEventByLecturerCategory(user.getId(), sort);
            else if (filter.equals("date"))
                event = repository.findAllEventByLecturerStartTimeEquals(user.getId(), sort, myDate);
            else if (filter.equals("past"))
                event = repository.findAllEventByLecturerStartTimeLessThan(user.getId(), sort, myDate);
            else if (filter.equals("future"))
                event = repository.findAllEventByLecturerStartTimeGreaterThan(user.getId(), sort, myDate);

            else if (filter.equals("cateId"))
                event = repository.findAllEventByLecturerCategoryId(user.getId(), cateId, sort);
            else if (filter.equals("catIdDate"))
                event = repository.findAllEventByLecturerCategoryIdAndEventStartTimeEquals(user.getId(), cateId, myDate);
            else if (filter.equals("cateIdPast"))
                event = repository.findAllEventByLecturerCategoryIdAndEventStartTimeLessThan(user.getId(), cateId, myDate, sort);
            else if (filter.equals("cateIdFuture"))
                event = repository.findAllEventByLecturerCategoryIdAndEventStartTimeGreaterThan(user.getId(), cateId, myDate, sort);
        }
        return event;
    }


    public Page<GetEventDTO> getSimpleEventAll(Pageable pageable) {
        List<GetEventDTO> listEventDTO = listMapper
                .mapList(getEvents(Sort.by("eventStartTime").descending(), null, null, null), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public GetEventDTO getSimpleEventById(int id) {
        String token = jwtRequestFilter.extractJwtFromRequest(request);
        String role = jwtTokenUtil.getAllClaimsFromToken(token).get("role").toString();
        Event event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking " + id + " Does Not Exist !!!"));

        String email = jwtTokenUtil.getAllClaimsFromToken(token).getSubject();
        User user = userRepository.findByEmail(email);
        if (token != null) {
            if (role.equals(Role.Student.name())) {
                checkEmail(event.getBookingEmail(), HttpStatus.FORBIDDEN);
            } else if (role.equals(Role.Lecturer.name())) {
                repository.findEventByLecturerAndEventID(user.getId(), id).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "No permission"));
            }
        }


        return modelMapper.map(event, GetEventDTO.class);
    }

    public Page<GetEventDTO> getSimpleEventDate(Instant date, Pageable pageable) {
        List<GetEventDTO> listEventDTO = listMapper.mapList(getEvents(Sort.by("eventStartTime").ascending(), date, null, "date"), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<GetEventDTO> getSimpleEventPastDate(Pageable pageable) {
        List<GetEventDTO> listEventDTO = listMapper.mapList(getEvents(Sort.by("eventStartTime").descending(), dateNow, null, "past"), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<GetEventDTO> getSimpleEventFutureDate(Pageable pageable) {
        List<GetEventDTO> listEventDTO = listMapper.mapList(getEvents(Sort.by("eventStartTime").ascending(), dateNow, null, "future"), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<GetEventDTO> getEventByCategory(int eventCategoryID, Pageable pageable) {
        List<GetEventDTO> listEventDTO = listMapper.mapList(getEvents(Sort.by("eventStartTime").descending(), null, eventCategoryID, "cateId"), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<GetEventDTO> getEventDateByCategory(int eventCategoryID, Instant date, Pageable pageable) {
        List<GetEventDTO> listEventDTO = listMapper.mapList(getEvents(null, date, eventCategoryID, "catIdDate"), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<GetEventDTO> getEventPastDateByCategory(int eventCategoryID, Pageable pageable) {
        List<GetEventDTO> listEventDTO = listMapper.mapList(getEvents(Sort.by("eventStartTime").descending(), dateNow, eventCategoryID, "cateIdPast"), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public Page<GetEventDTO> getEventFutureDateByCategory(int eventCategoryID, Pageable pageable) {
        List<GetEventDTO> listEventDTO = listMapper.mapList(getEvents(Sort.by("eventStartTime").ascending(), dateNow, eventCategoryID, "cateIdFuture"), GetEventDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, listEventDTO);
    }

    public void delete(int eventID) {
        String token = jwtRequestFilter.extractJwtFromRequest(request);
        Event event = repository.findById(eventID).orElseThrow(() -> new RuntimeException(eventID + " Does not exit !!!"));
        if (token != null) {
            if (jwtTokenUtil.getAllClaimsFromToken(token).get("role").toString().equals(Role.Student.name()))
                checkEmail(event.getBookingEmail(), HttpStatus.FORBIDDEN);
        }
        repository.deleteById(eventID);
    }

    // check overlapping
    public void checkOverlapping(Instant startime, int categoryId) {
        Eventcategory eventcategory = cateRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(categoryId + "Does not exit !!!"));
        List<Event> listAll = repository.findByEventCategoryID(categoryId, Sort.by("eventStartTime").descending());

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
        if (email.equals(jwtTokenUtil.getAllClaimsFromToken(token).getSubject()) == false) {
            throw new ResponseStatusException(status, "the booking email must be the same as the student's email");
        }

    }

    public Event add(AddEventDTO newEvent, MultipartFile files) throws MessagingException, IOException {
        Eventcategory eventcategory = cateRepository.findById(newEvent.getEventCategoryID())
                .orElseThrow(() -> new RuntimeException(newEvent.getEventCategoryID() + "Does not exit !!!"));
        Event event = new Event();
        String file = null;
        if (files.isEmpty() == false) {
            file = StringUtils.cleanPath(files.getOriginalFilename());
        }
        String token = jwtRequestFilter.extractJwtFromRequest(request);
        if (token != null) {
            if (jwtTokenUtil.getAllClaimsFromToken(token).get("role").toString().equals(Role.Student.name())) {
                checkEmail(newEvent.getBookingEmail(), HttpStatus.BAD_REQUEST);
            }
        }

        checkOverlapping(newEvent.getEventStartTime(), newEvent.getEventCategoryID());

        event.setBookingName(newEvent.getBookingName());
        event.setBookingEmail(newEvent.getBookingEmail());
        event.setEventNotes(newEvent.getEventNotes());
        event.setEventStartTime(newEvent.getEventStartTime());
        event.setEventCategoryID(newEvent.getEventCategoryID());
        event.setEventDuration(eventcategory.getEventDuration());
        event.setEventCategory(eventcategory.getEventCategoryName());
        event.setFileName(file);
        event.setFilesData(newEvent.getFileData());

        Event event1 = modelMapper.map(event, Event.class);
        repository.saveAndFlush(event1);
        sendmail(event);
        return event;

    }


    public Event update(EditEventDTO updateEvent, int bookingId) {

        if (updateEvent.getEventStartTime() != null) {
            Event event = repository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Bookind ID " + bookingId + "Does not exit !!!"));

            String token = jwtRequestFilter.extractJwtFromRequest(request);
            if (token != null) {
                if (jwtTokenUtil.getAllClaimsFromToken(token).get("role").toString().equals(Role.Student.name())) {
                    checkEmail(event.getBookingEmail(), HttpStatus.FORBIDDEN);
                }
            }
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd, yyyy HH:mm").withZone(ZoneId.of("UTC"));
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm ").withZone(ZoneId.of("UTC"));
        Instant endTime = event.getEventStartTime().plusSeconds(event.getEventDuration() * 60);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("oasip.kw3.noreply@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(event.getBookingEmail()));
        msg.setSubject("Your booking is complete.");
        msg.setContent("Subject: [OASIP] " + event.getEventCategory() + " @ " + formatter.format(event.getEventStartTime()) + " - " + formatter1.format(endTime) + " (ICT)" +
                        "<br>Reply-to: noreply@intproj21.sit.kmutt.ac.th" +
                        "<br>Booking Name: " + event.getBookingName() +
                        "<br>Event Category: " + event.getEventCategory() +
                        "<br>When: " + formatter.format(event.getEventStartTime()) + " - " + formatter1.format(endTime) + " (ICT)" +
                        "<br>Event Notes: " + event.getEventNotes()

                , "text/html; charset=utf-8");
        msg.setSentDate(new Date());

        Transport.send(msg);
    }

}