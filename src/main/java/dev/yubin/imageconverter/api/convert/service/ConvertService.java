package dev.yubin.imageconverter.api.convert.service;

import dev.yubin.imageconverter.api.convert.enums.ImageFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvertService {
    public void convert(MultipartFile file, ImageFormat format) {
        // TODO: 여기서 파일 저장하고, RabbitMQ로 작업 메시지 전송
        System.out.println("파일 이름: " + file.getOriginalFilename());
        System.out.println("변환 포맷: " + format.name());

        // 예: rabbitTemplate.convertAndSend(...) or 저장 경로 생성 등
    }
}
