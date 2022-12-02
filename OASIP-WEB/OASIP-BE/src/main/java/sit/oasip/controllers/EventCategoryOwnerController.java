package sit.oasip.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sit.oasip.services.EventCategoryOwnerService;

@CrossOrigin
@RestController
@RequestMapping("/api/owners")
public class EventCategoryOwnerController {
    @Autowired
    public EventCategoryOwnerService eventCategoryOwnerService;

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/{ownerId}")
    @ResponseStatus(value = HttpStatus.OK,reason = "delete successful !!!")
    public void delete(@PathVariable Integer ownerId){
        eventCategoryOwnerService.delete(ownerId);
    }
}
