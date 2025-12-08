package tech.csm.vsreq.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class FileUtil {

    @Value("${upload.dir}")
    private String uploadDir;

    // return folder path
    public String getDirPath() {
        return uploadDir;
    }

    // upload the file and return filename
    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Path destination = Paths.get(uploadDir + fileName);

        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }

        return fileName;
    }
}
