package ru.sanctio.models.maper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.sanctio.models.entity.Address;
import ru.sanctio.models.dto.AddressDTO;

import java.util.List;

@Mapper
public interface AddressDTOMapper {

    AddressDTOMapper INSTANCE = Mappers.getMapper( AddressDTOMapper.class );

    Address mapToEntity(AddressDTO addressDTO);

    AddressDTO mapToDto(Address address);

    List<AddressDTO> mapToDtoList(List<Address> addresses);
}
