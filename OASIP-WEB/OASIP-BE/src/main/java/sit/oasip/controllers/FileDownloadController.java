package sit.oasip.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sit.oasip.entities.Event;
import sit.oasip.repositories.EventRepository;
import sit.oasip.services.FileService;

@RestController
@RequestMapping("/api/download")
public class FileDownloadController {
    private final EventRepository repository;
    private final FileService fileService;

    public FileDownloadController(EventRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Integer id) {
        Event fileDB = repository.findByBookingId(id);
        if (fileDB.getFileName() == null ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No files to download !!");
        }
        Resource file = fileService.loadFileAsResource(fileDB.getFileName());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFileName() + "\"")
                .body(file);
    }
}
