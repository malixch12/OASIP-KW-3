//package sit.oasip.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//import sit.oasip.dtos.FileDTO.UploadFileDTO;
//import sit.oasip.entities.Event;
//
//import javax.mail.MessagingException;
//import java.io.IOException;
//
//public class FileStorageService {
//    @Autowired
//    private EventService eventService;
//
//    public void store(MultipartFile file) throws IOException, MessagingException {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        UploadFileDTO FileDB = new UploadFileDTO(file.getBytes(),fileName);
//
//        eventService.add(null,FileDB);
//    }
//}
