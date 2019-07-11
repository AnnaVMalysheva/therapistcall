package ru.therapistcall.mappers;

import org.modelmapper.ModelMapper;
import ru.therapistcall.dtos.AddressDto;
import ru.therapistcall.entities.Address;

public class AddressMapper {

    private static ModelMapper mapper = new ModelMapper();

    public static Address asEntity(AddressDto dto) {
        return mapper.map(dto, Address.class);
    }
}
