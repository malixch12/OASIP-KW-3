package sit.oasip.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.oasip.dtos.GrantOwner;
import sit.oasip.entities.EventCategoryOwner;
import sit.oasip.entities.Eventcategory;
import sit.oasip.entities.User;
import sit.oasip.repositories.EventCategoryOwnerRepository;
import sit.oasip.repositories.EventcategoryRepository;
import sit.oasip.repositories.UserRepository;
import sit.oasip.utils.Role;

import java.util.List;
import java.util.Optional;

@Service
public class EventCategoryOwnerService {
    @Autowired
    private EventCategoryOwnerRepository eventCategoryOwnerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventcategoryRepository eventcategoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public void delete(int ownerId) {
        EventCategoryOwner eco = eventCategoryOwnerRepository.findById(ownerId);
        List<EventCategoryOwner> ownerList = eventCategoryOwnerRepository.findByEventCategoryID(eco.getEventCategoryID());
        if (ownerList.size() > 1) {
            eventCategoryOwnerRepository.deleteById(ownerId);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This lecturer cannot be removed because there is only one lecturer left.");
        }
        System.out.println(ownerList.size());
    }

    public EventCategoryOwner grantOwner(GrantOwner owner) {
        EventCategoryOwner eco = new EventCategoryOwner();

        Optional<User> u = userRepository.findById(owner.getUserId());
        Eventcategory ec = eventcategoryRepository.findByEventCategoryID(owner.getEventCategoryID());
        if (eventCategoryOwnerRepository.findByEventCategoryIDAndAndUserID(ec, u.get()) == null) {
            if(u.get().getRole() == Role.Lecturer.name()){
                eco.setEventCategoryID(ec);
                eco.setUserID(u.get());
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,u.get().getRole() + " cannot add owner.");
            }

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,u.get().getUserName() + " already owns " + ec.getEventCategoryName() + " .");
        }
        eco.setEventCategoryID(ec);
        eco.setUserID(u.get());
        return eventCategoryOwnerRepository.saveAndFlush(modelMapper.map(eco, EventCategoryOwner.class));
    }
}