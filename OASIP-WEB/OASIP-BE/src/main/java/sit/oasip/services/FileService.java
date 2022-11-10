package sit.oasip.services;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sit.oasip.Component.FileStorageProperties;
import sit.oasip.entities.Event;
import sit.oasip.repositories.EventRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Getter @Setter
    public String name;

    public String store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        int dot = fileName.lastIndexOf('.');
        fileName= fileName.substring(0,dot)+"_"+Instant.now().toEpochMilli()+fileName.substring(dot);

        if (fileName.contains("..")) {
            throw new RuntimeException("Filename contains invalid path sequence" + fileName);
        }
        setName(fileName);
        Path targetLocation = this.filePath.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    public Resource loadFileAsResource(String fileName){
        try {
            Path filePath = this.filePath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()){
                return resource;
            }else {
                throw new RuntimeException("File not found "+fileName);
            }
        }catch (MalformedURLException ex){
            throw new RuntimeException("File not found "+fileName,ex);
        }
    }
}
