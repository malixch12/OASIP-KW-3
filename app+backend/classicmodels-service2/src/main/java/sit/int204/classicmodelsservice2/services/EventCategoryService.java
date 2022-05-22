package sit.int204.classicmodelsservice2.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.classicmodelsservice2.dtos.SimpleEventDTO;
import sit.int204.classicmodelsservice2.dtos.SimpleEventcategoryDTO;
import sit.int204.classicmodelsservice2.entities.Event;
import sit.int204.classicmodelsservice2.entities.Eventcategory;
import sit.int204.classicmodelsservice2.repositories.EventcategoryRepository;
import sit.int204.classicmodelsservice2.utils.ListMapper;

@Service
public class EventCategoryService {

    private final EventcategoryRepository repository;

    private ListMapper listMapper = new ListMapper();
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public EventCategoryService(EventcategoryRepository repository) {
        this.repository = repository;
    }

    public List<SimpleEventcategoryDTO> getSimpleEventcategoryAll() {
        return listMapper.mapList(repository.findAll(), SimpleEventcategoryDTO.class, modelMapper);
    }

    public SimpleEventcategoryDTO getSimpleEventcategoryById(Integer id) {
        Eventcategory eventcategory = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + " Does Not Exist !!!"));

        return modelMapper.map(eventcategory, SimpleEventcategoryDTO.class);
    }

    public SimpleEventcategoryDTO update(SimpleEventcategoryDTO updateCategory, Integer id) {
        List<Eventcategory> listCateAll = repository.findAll();

        for (int i = 0; i < listCateAll.size(); i++) {
            if (listCateAll.get(i).getEventCategoryID() != id) {
                String cateName = listCateAll.get(i).getEventCategoryName().toLowerCase();
                if (cateName.equals(updateCategory.getEventCategoryName().toLowerCase())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is not unique");
                }
            }
        }
        Eventcategory eventCate = repository.findById(id).map(c -> {
            c.setEventCategoryName(updateCategory.getEventCategoryName());
            c.setEventDuration(updateCategory.getEventDuration());
            c.setEventCategoryDescription(updateCategory.getEventCategoryDescription());
            return repository.saveAndFlush(c);

        }).orElseThrow(() -> new RuntimeException("Can not update !!!"));
        return modelMapper.map(eventCate, SimpleEventcategoryDTO.class);
    }
}
