package dev.yubin.imageconverter.api.convert.util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class NfsUtil {

    @Value("${nfs.root}")
    private String nfsRoot;

    public String saveFileToNfs(MultipartFile file, String userId, String requestId) throws IOException {
        String folderName = userId + "-" + requestId;
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"));
        String originalExt = getExtension(
                Optional.ofNullable(file.getOriginalFilename()).orElse("default.png")
        );
        Path dirPath = Path.of(nfsRoot, folderName);
        File dir = dirPath.toFile();
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new IOException("Failed to create directory: " + dir.getAbsolutePath());
            }
        }

        String fileName = timestamp + "." + originalExt;
        Path filePath = dirPath.resolve(fileName);

        file.transferTo(filePath);
        return filePath.toString(); // Rust가 직접 접근할 경로
    }

    private String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        return (dotIndex != -1) ? filename.substring(dotIndex + 1) : "png";
    }
}
