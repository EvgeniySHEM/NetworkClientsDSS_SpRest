package ru.sanctio.service;

import ru.sanctio.models.dto.AddressDTO;

import java.util.List;

/**
 * Интерфейс для реализации бизнес логики с данными адреса.
 */
public interface AddressService {
    /**
     * Возвращает информацию о всех данных клиентов, хранящихся в базе данных.
     * @return List<AddressDTO>
     */
    List<AddressDTO> getSortedData();

    /**
     * Возвращает данные адреса, хранящиеся в базе данных, по его идентификатору.
     * @param addressId
     * @return AddressDTO
     */
    AddressDTO selectAddressById(String addressId);

    /**
     * Удаляет данные адреса, хранящиеся в базе данных, по его идентификатору.
     * @param addressId
     */
    void deleteAddress(String addressId);
}
