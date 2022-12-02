package sit.oasip.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.oasip.entities.EventCategoryOwner;
import sit.oasip.repositories.EventCategoryOwnerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventCategoryOwnerService {
    @Autowired
    private EventCategoryOwnerRepository repository;


    public void delete(int ownerId) {
        EventCategoryOwner eco = repository.findById(ownerId);
        List<EventCategoryOwner> ownerList = repository.findByEventCategoryID(eco.getEventCategoryID());
        if (ownerList.size() > 1) {
            repository.deleteById(ownerId);
        }else {
            throw new RuntimeException("This lecturer cannot be removed because there is only one lecturer left.");
        }
        System.out.println(ownerList.size());
    }
    
}
