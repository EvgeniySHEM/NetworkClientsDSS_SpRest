package ru.sanctio.dao;

import ru.sanctio.models.entity.Address;

import java.util.List;

/**
 * Интерфейс для взаимодействия с базой данных,
 * касающегося работы с данными адреса
 */
public interface DBManagerAddress {
    List<Address> getAllInformation();

    Address selectAddressById(String addressId);

    void deleteAddressById(String addressId);
}
