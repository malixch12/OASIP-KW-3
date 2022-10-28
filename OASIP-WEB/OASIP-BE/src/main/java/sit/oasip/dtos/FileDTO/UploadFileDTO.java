package sit.oasip.dtos.FileDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UploadFileDTO {

    private byte[] FileData;
    private String FileName;

    public UploadFileDTO(byte[] fileData, String fileName) {
        this.FileData=fileData;
        this.FileName=fileName;
    }
}
