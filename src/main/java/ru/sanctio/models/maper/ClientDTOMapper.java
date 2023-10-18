package ru.sanctio.models.maper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.sanctio.models.entity.Client;
import ru.sanctio.models.dto.ClientDTO;

@Mapper
public interface ClientDTOMapper {

    ClientDTOMapper INSTANCE = Mappers.getMapper( ClientDTOMapper.class );

    Client mapToEntity (ClientDTO clientDTO);
    ClientDTO mapToDto (Client client);
}
