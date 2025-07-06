package dev.yubin.imageconverter.api.convert;

import dev.yubin.imageconverter.api.common.exception.NotFoundException;
import dev.yubin.imageconverter.api.config.RabbitMQProperties;
import dev.yubin.imageconverter.api.convert.service.ConvertService;
import dev.yubin.imageconverter.api.convert.util.NfsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConvertServiceTest {

    private RedisTemplate<String, String> redisTemplate;
    private RabbitTemplate rabbitTemplate;
    private RabbitMQProperties rabbitMQProperties;
    private NfsUtil nfsUtil;

    private ConvertService convertService;

    @BeforeEach
    void setUp() {
        redisTemplate = mock(RedisTemplate.class);
        rabbitTemplate = mock(RabbitTemplate.class);
        rabbitMQProperties = mock(RabbitMQProperties.class);
        nfsUtil = mock(NfsUtil.class);

        convertService = new ConvertService(rabbitTemplate, rabbitMQProperties, redisTemplate, nfsUtil);
    }

    @Test
    void getAsciiResultUrlOrThrow_shouldReturnFullUrl_whenKeyExists() {
        String requestId = "ABC123";
        String hostUrl = "http://localhost";
        String valueInRedis = "/ascii/ABC123.txt";

        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("ascii_result:" + requestId)).thenReturn(valueInRedis);

        String result = convertService.getAsciiResultUrlOrThrow(requestId, hostUrl);

        assertEquals("http://localhost/ascii/ABC123.txt", result);
    }

    @Test
    void getAsciiResultUrlOrThrow_shouldThrowException_whenKeyMissing() {
        String requestId = "DEF456";
        String hostUrl = "http://localhost/";

        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("ascii_result:" + requestId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> {
            convertService.getAsciiResultUrlOrThrow(requestId, hostUrl);
        });
    }

    @Test
    void saveAsciiResult_shouldCallRedisSet_withCorrectKeyAndValue() {
        String requestId = "XYZ789";
        String txtUrl = "/ascii/XYZ789.txt";
        String expectedKey = "ascii_result:" + requestId;

        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        convertService.saveAsciiResult(requestId, txtUrl);

        verify(valueOperations, times(1)).set(expectedKey, txtUrl);
    }
}