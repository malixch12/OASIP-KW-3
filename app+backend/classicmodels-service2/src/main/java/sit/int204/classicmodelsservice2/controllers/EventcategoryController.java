package sit.int204.classicmodelsservice2.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sit.int204.classicmodelsservice2.dtos.SimpleEventcategoryDTO;
import sit.int204.classicmodelsservice2.entities.Eventcategory;
import sit.int204.classicmodelsservice2.repositories.EventcategoryRepository;
import sit.int204.classicmodelsservice2.services.EventCategoryService;
import org.springframework.http.HttpStatus;

import java.util.List;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RequestMapping("/api/eventcategorys")
public class EventcategoryController {

    @Autowired
    private EventCategoryService eventcategoryService;
    private final EventcategoryRepository repository;

    @Autowired
    public EventcategoryController(EventcategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<SimpleEventcategoryDTO> getEventcategoryByAll() {
        return eventcategoryService.getSimpleEventcategoryAll();
    }

    @GetMapping("/{id}")
    public SimpleEventcategoryDTO getEventcategoryById(@PathVariable Integer id) {
        return eventcategoryService.getSimpleEventcategoryById(id);
    }

    @PutMapping("/{id}")
    public SimpleEventcategoryDTO edit(@RequestBody SimpleEventcategoryDTO updateCategory, @PathVariable Integer id) {
       return eventcategoryService.update(updateCategory,id);
    }

}
