package sit.oasip.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sit.oasip.dtos.SimpleEventcategoryDTO;
import sit.oasip.repositories.EventcategoryRepository;
import sit.oasip.services.EventCategoryService;
import org.springframework.http.HttpStatus;

import java.util.List;

import javax.validation.Valid;

@RestController
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
    @ResponseStatus(HttpStatus.OK)
    public SimpleEventcategoryDTO edit(@Valid @RequestBody SimpleEventcategoryDTO updateCategory, @PathVariable Integer id) {
       return eventcategoryService.update(updateCategory,id);
    }

}
