package sit.oasip.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sit.oasip.entities.Event;
import sit.oasip.repositories.EventRepository;
import sit.oasip.services.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/file")
public class FileDownloadController {
    private final EventRepository repository;
    private final FileService fileService;

    public FileDownloadController(EventRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    @PreAuthorize("hasAnyAuthority('Admin','Student','Lecturer')")
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Integer id) {
        Event fileDB = repository.findByBookingId(id);
        if (fileDB.getFileName() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No files to download !!");
        }
        Resource file = fileService.loadFileAsResource(fileDB.getFileName());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFileName() + "\"")
                .body(file);
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping("/downloadZip")
    public void getZipfile(HttpServletResponse response) {
        fileService.loadAllFile(response);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Student')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws IOException {
        fileService.delete(id);
    }
}
