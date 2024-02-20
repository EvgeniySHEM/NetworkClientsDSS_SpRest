package ru.sanctio.models.maper;

import org.mapstruct.Mapper;
import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.models.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressDTOMapper extends Mappable<Address, AddressDTO> {
}
