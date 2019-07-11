package ru.therapistcall.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CoordinateDto {
    @JsonProperty("response")
    ResponseDto response;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class ResponseDto {
        @JsonProperty("GeoObjectCollection")
        GeoObjectCollectionDto geoObjectCollection;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class GeoObjectCollectionDto {
        @JsonProperty("featureMember")
        List<FeatureMemberDto> featureMember;

        @JsonProperty("metaDataProperty")
        MetaDataPropertyDto metaDataProperty;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class FeatureMemberDto {
        @JsonProperty("GeoObject")
        GeoObjectDto geoObject;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class GeoObjectDto {
        @JsonProperty("Point")
        PointDto point;

        @JsonProperty("AddressDetails")
        AddressDetailsDto addressDetails;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class PointDto {
        @JsonProperty("pos")
        String pos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class AddressDetailsDto {
        @JsonProperty("Country")
        CountryDto country;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class CountryDto {
        @JsonProperty("AddressLine")
        String addressLine;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MetaDataPropertyDto {
        @JsonProperty("GeocoderResponseMetaData")
        GeocoderResponseMetaDataDto geocoderResponseMetaData;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class GeocoderResponseMetaDataDto {
        @JsonProperty("found")
        Integer found;
    }
}