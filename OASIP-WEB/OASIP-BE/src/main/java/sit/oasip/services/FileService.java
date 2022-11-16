package sit.oasip.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sit.oasip.Component.FileStorageProperties;
import sit.oasip.entities.Event;
import sit.oasip.repositories.EventRepository;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileService {

    private final EventRepository eventRepository;
    private Path filePath;

    @Autowired
    public FileService(EventRepository eventRepository, FileStorageProperties fileStorageProperties) {
        this.eventRepository = eventRepository;
        this.filePath = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(filePath);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory", ex);
        }
    }

    @Getter
    @Setter
    public String name;

    public String store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        int dot = fileName.lastIndexOf('.');
        fileName = fileName.substring(0, dot) + "_" + Instant.now().toEpochMilli() + fileName.substring(dot);

        if (fileName.contains("..")) {
            throw new RuntimeException("Filename contains invalid path sequence" + fileName);
        }
        setName(fileName);
        Path targetLocation = this.filePath.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.filePath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }

    public void loadAllFile(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=download.zip");
        response.setStatus(HttpServletResponse.SC_OK);

        List<String> fileNames = eventRepository.findFileName();
        fileNames.removeAll(Collections.singleton(null));
        System.out.println(fileNames);
        try (ZipOutputStream zippedOut = new ZipOutputStream(response.getOutputStream())) {
            for (String file : fileNames) {
                Path filePath = this.filePath.resolve(file).normalize();
                FileSystemResource resource = new FileSystemResource(filePath);

                ZipEntry e = new ZipEntry(resource.getFilename());
                // Configure the zip entry, the properties of the file
                e.setSize(resource.contentLength());
                e.setTime(System.currentTimeMillis());
                // etc.
                zippedOut.putNextEntry(e);
                // And the content of the resource:
                StreamUtils.copy(resource.getInputStream(), zippedOut);
                zippedOut.closeEntry();
            }
            zippedOut.finish();
        } catch (Exception e) {
            // Exception handling goes here
        }
    }


    public void delete(int id) throws IOException {

        Event event = eventRepository.findByBookingId(id);
        if (event != null) {
            if (event.getFileName() != null) {
                String fileName = "../db/file-uploads/" + event.getFileName();
                Files.delete(Paths.get(fileName));
                event.setFileName(null);
                eventRepository.saveAndFlush(event);
                throw new ResponseStatusException(HttpStatus.OK, "Delete successful !!");
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No files to delete");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No files to delete");
        }

    }
}
