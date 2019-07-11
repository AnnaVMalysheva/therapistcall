package ru.therapistcall.yandex_integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.therapistcall.dtos.CoordinateDto;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "yandex-geocode-client", url = "${yandex.apiUrl}")
public interface YandexGeocodeApi {

    @RequestMapping(method = GET, path = "/")
    CoordinateDto getCoordinatesByGeocode(@RequestParam(name = "geocode") String geocode, @RequestParam(name = "apikey") String apiKey,
                                          @RequestParam(name = "format", defaultValue = "json") String format);
}