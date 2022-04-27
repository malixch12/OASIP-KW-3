package sit.int204.classicmodelsservice2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sit.int204.classicmodelsservice2.dtos.SimpleEventcategoryDTO;
import sit.int204.classicmodelsservice2.entities.Eventcategory;
import sit.int204.classicmodelsservice2.repositories.EventcategoryRepository;
import sit.int204.classicmodelsservice2.services.EventCategoryService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RequestMapping("/api/eventcategorys")
public class EventcategoryController {

    @Autowired
    private EventCategoryService EventcategoryService;

    private final EventcategoryRepository repository ;

    @Autowired
    public EventcategoryController(EventcategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<Eventcategory> getEventcategoryByAll() {
        return EventcategoryService.getSimpleEventcategoryAll();
    }
    
    @GetMapping("/{id}")
    public SimpleEventcategoryDTO getEventcategoryById(@PathVariable Integer id) {
        return EventcategoryService.getSimpleEventcategoryById(id);
    }
}
