package ru.sanctio.service;

import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.models.dto.ClientDTO;

/**
 * Интерфейс для реализации бизнес логики с данными клиента.
 */
public interface ClientService {
    /**
     * Метод для обработки данных о клиенте перед сохранением в базе данных
     * @param clientDTO
     * @param addressDTO
     * @return boolean
     */
    boolean createNewClient(ClientDTO clientDTO, AddressDTO addressDTO);

    /**
     * Метод для обработки данных о клиенте перед изменениями в базе данных
     * @param addressDTO
     * @return AddressDTO
     */
    AddressDTO updateClient(AddressDTO addressDTO);

    /**
     * Метод для обработки запрашиваемых данных о клиенте,
     * имеющиеся в базе данных,
     * по его идентификатору
     * @param id
     * @return ClientDTO
     */
    ClientDTO getClientById(String id);
}
