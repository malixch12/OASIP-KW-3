package sit.oasip.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.oasip.Component.JwtTokenUtil;
import sit.oasip.config.JwtRequestFilter;
import sit.oasip.dtos.EventCategoryDTO.EditEventcategoryDTO;
import sit.oasip.dtos.EventCategoryDTO.GetEventCategoryDTO;
import sit.oasip.entities.Eventcategory;
import sit.oasip.repositories.EventcategoryRepository;
import sit.oasip.repositories.UserRepository;
import sit.oasip.utils.ListMapper;
import sit.oasip.utils.Role;

import javax.servlet.http.HttpServletRequest;

@Service
public class EventCategoryService {

    private final EventcategoryRepository repository;
    private final UserRepository userRepository;

    @Autowired
    private ListMapper listMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    private HttpServletRequest request;

    @Autowired
    public EventCategoryService(EventcategoryRepository repository, UserRepository userRepository, HttpServletRequest request) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.request = request;
    }

    private List<Eventcategory> getAllEventCate() {
        List<Eventcategory> eventcategories ;

        String token = jwtRequestFilter.getJwtToken();
        if(token != null){
            String email = jwtTokenUtil.getAllClaimsFromToken(token).getSubject();
            String role = jwtTokenUtil.getAllClaimsFromToken(token).get("roles").toString();
            if (role.equals(Role.Lecturer.name())) {
                eventcategories = repository.findEventCateByLecturer(userRepository.findByEmail(email).getUserId());
            } else {
                eventcategories = repository.findAll();
            }
        }else{
            eventcategories = repository.findAll();
        }

        return eventcategories;
    }

    private Eventcategory getEventCate(int cateId) {
        Eventcategory eventcategory = new Eventcategory();

        String email = jwtTokenUtil.getAllClaimsFromToken(jwtRequestFilter.getJwtToken()).getSubject();
        String role = jwtTokenUtil.getAllClaimsFromToken(jwtRequestFilter.getJwtToken()).get("roles").toString();

        if (role.equals(Role.Lecturer.name())) {
            eventcategory = repository.findEventCateByLecturerAndCateID(userRepository.findByEmail(email).getUserId(), cateId).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "No Permission !!"));
        } else {
            eventcategory = repository.findById(cateId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category ID : " + cateId + " Does Not Exist !!!"));

        }
        return eventcategory;
    }


    public List<GetEventCategoryDTO> getSimpleEventcategoryAll() {
        List<Eventcategory> getCateLists = repository.findAll();
        return listMapper.mapList(getAllEventCate(), GetEventCategoryDTO.class, modelMapper);
    }

    public GetEventCategoryDTO getSimpleEventcategoryById(Integer id) {
        return modelMapper.map(getEventCate(id), GetEventCategoryDTO.class);
    }

    public GetEventCategoryDTO update(EditEventcategoryDTO updateCategory, Integer id) {
        List<Eventcategory> listCateAll = repository.findAll();

        // category name unique
        for (int i = 0; i < listCateAll.size(); i++) {
            if (listCateAll.get(i).getEventCategoryID() != id) {
                String cateName = listCateAll.get(i).getEventCategoryName().toLowerCase();
                if (cateName.equals(updateCategory.getEventCategoryName().toLowerCase().trim())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is not unique");
                }
            }
        }

        Eventcategory eventCate = repository.findById(id).map(c -> {
            c.setEventCategoryName(updateCategory.getEventCategoryName().trim());
            c.setEventDuration(updateCategory.getEventDuration());
            c.setEventCategoryDescription(updateCategory.getEventCategoryDescription());
            return repository.saveAndFlush(c);

        }).orElseThrow(() -> new RuntimeException("Can not update !!!"));
        return modelMapper.map(eventCate, GetEventCategoryDTO.class);
    }
}
