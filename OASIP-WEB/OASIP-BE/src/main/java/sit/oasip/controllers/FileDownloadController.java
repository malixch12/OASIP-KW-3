package sit.oasip.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.oasip.entities.Event;
import sit.oasip.repositories.EventRepository;

@RestController
@RequestMapping("/api/download")
public class FileDownloadController {
    private final EventRepository repository;

    public FileDownloadController(EventRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
        Event fileDB = repository.findByBookingId(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFileName() + "\"")
                .body(fileDB.getFilesData());
    }
}
