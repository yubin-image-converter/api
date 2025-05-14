package dev.yubin.imageconverter.api.convert.util;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class NfsUtil {

    @Value("${nfs.root}")
    private String nfsRoot;

    public String saveFileToNfs(MultipartFile file, String userId, String requestId) throws IOException {
        String folderName = userId + "-" + requestId;
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"));
        String originalFilename = Optional.ofNullable(file.getOriginalFilename()).orElse("default.png");
        String originalExt = getExtension(originalFilename);

        Path dirPath = Path.of(nfsRoot, folderName);
        File dir = dirPath.toFile();

        log.info("[NFS] Root path: {}", nfsRoot);
        log.info("[NFS] Target directory: {}", dir.getAbsolutePath());
        log.info("[NFS] Original file name: {}", originalFilename);
        log.info("[NFS] Extension: {}", originalExt);

        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                log.info("[NFS] Directory created: {}", dir.getAbsolutePath());
            } else {
                log.error("[NFS] Failed to create directory: {}", dir.getAbsolutePath());
                throw new IOException("Failed to create directory: " + dir.getAbsolutePath());
            }
        } else {
            log.info("[NFS] Directory already exists: {}", dir.getAbsolutePath());
        }

        String fileName = timestamp + "." + originalExt;
        Path filePath = dirPath.resolve(fileName);

        log.info("[NFS] Final file path: {}", filePath);

        file.transferTo(filePath);
        log.info("[NFS] File saved successfully: {}", filePath);

        return filePath.toString();
    }

    private String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        return (dotIndex != -1) ? filename.substring(dotIndex + 1) : "png";
    }

    public String getRoot() {
        return nfsRoot;
    }

}
