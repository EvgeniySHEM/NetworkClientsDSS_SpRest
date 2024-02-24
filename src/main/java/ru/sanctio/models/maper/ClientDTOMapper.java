package ru.sanctio.models.maper;

import org.mapstruct.Mapper;
import ru.sanctio.models.dto.ClientDTO;
import ru.sanctio.models.entity.Client;

/**
 * Интерфейс для реализации маппинга данных клиента
 */
@Mapper(componentModel = "spring")
public interface ClientDTOMapper extends Mappable<Client, ClientDTO> {
}
