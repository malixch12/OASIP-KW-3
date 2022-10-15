package sit.oasip.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.oasip.dtos.SimpleEventcategoryDTO;
import sit.oasip.entities.Event;
import sit.oasip.entities.EventCategoryOwner;
import sit.oasip.entities.Eventcategory;
import sit.oasip.repositories.EventcategoryRepository;
import sit.oasip.utils.ListMapper;

import javax.servlet.http.HttpServletRequest;

@Service
public class EventCategoryService {

    private final EventcategoryRepository repository;

    @Autowired
    private ListMapper listMapper;
    @Autowired
    private ModelMapper modelMapper;

    private HttpServletRequest request;
    @Autowired
    public EventCategoryService(EventcategoryRepository repository, HttpServletRequest request) {
        this.repository = repository;
        this.request=request;
    }

//    private List<Eventcategory> getEventCateByLecturer(List<EventCategoryOwner> owners, List<Eventcategory> getCateLists) {
//        List<Eventcategory> eventByEventCateId = new ArrayList<>();
//        for (int i = 0; i < getCateLists.size(); i++) {
//            for (int j = 0; j < owners.size(); j++) {
//                if (getCateLists.get(i).getEventCategoryID().equals(owners.get(j).getEventCategoryID().getEventCategoryID())) {
//                    eventByEventCateId.add(getCateLists.get(i));
//                }
//            }
//
//        }
//        return eventByEventCateId;
//    }

    public List<SimpleEventcategoryDTO> getSimpleEventcategoryAll() {
        List<Eventcategory> getCateLists = repository.findAll();
        return listMapper.mapList(getCateLists, SimpleEventcategoryDTO.class, modelMapper);
    }

    public SimpleEventcategoryDTO getSimpleEventcategoryById(Integer id) {
        Eventcategory eventcategory = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + " Does Not Exist !!!"));

        return modelMapper.map(eventcategory, SimpleEventcategoryDTO.class);
    }

    public SimpleEventcategoryDTO update(SimpleEventcategoryDTO updateCategory, Integer id) {
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
        return modelMapper.map(eventCate, SimpleEventcategoryDTO.class);
    }
}
