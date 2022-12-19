package sit.oasip.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sit.oasip.dtos.EventCategoryDTO.EditEventcategoryDTO;
import sit.oasip.dtos.EventCategoryDTO.GetEventCategoryDTO;
import sit.oasip.entities.Eventcategory;
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

    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer','Guest')")
    @GetMapping("")
    public List<GetEventCategoryDTO> getEventcategoryByAll() {
        return eventcategoryService.getSimpleEventcategoryAll();
    }


    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer','Guest')")
    @GetMapping("/{id}")
    public GetEventCategoryDTO getEventcategoryById(@PathVariable Integer id) {
        return eventcategoryService.getSimpleEventcategoryById(id);
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetEventCategoryDTO edit(@Valid @RequestBody EditEventcategoryDTO updateCategory, @PathVariable Integer id) {
       return eventcategoryService.update(updateCategory,id);
    }

}
