//package sit.oasip.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import sit.oasip.entities.Event;
//import sit.oasip.services.FileStorageService;
//
//import javax.mail.MessagingException;
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api/files")
//public class FileController {
//    @Autowired
//    private FileStorageService storageService;
//
//    @PostMapping("/upload")
//    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException, MessagingException {
//         storageService.store(file);
//    }
//}
