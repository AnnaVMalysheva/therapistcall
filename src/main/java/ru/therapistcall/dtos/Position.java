package ru.therapistcall.dtos;

import com.vividsolutions.jts.geom.Coordinate;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class Position {

    private Coordinate coordinate;

    @Builder
    public Position(String yandexPos) {
        if(StringUtils.isEmpty(yandexPos)) {
            this.coordinate = new Coordinate();
        } else {
            String[] coordinates;
            coordinates = yandexPos.split(" ");
            this.coordinate = new Coordinate(Double.valueOf(coordinates[0]), Double.valueOf(coordinates[1]));
        }
    }
}
