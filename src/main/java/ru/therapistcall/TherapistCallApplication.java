package ru.therapistcall;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.therapistcall.yandex_integration.YandexGeocodeApi;

@SpringBootApplication
@EnableConfigurationProperties({TherapistCallApplication.YandexKeyHolder.class})
@EnableFeignClients(clients = {YandexGeocodeApi.class})
public class TherapistCallApplication {

    public static void main(String[] args) {
        SpringApplication.run(TherapistCallApplication.class, args);
    }

    @Data
    @ConfigurationProperties("yandex")
    public static class YandexKeyHolder {
        private String apiKey;
    }
}