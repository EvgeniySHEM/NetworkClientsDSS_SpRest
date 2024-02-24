package ru.sanctio.dao;

import ru.sanctio.models.entity.Address;
import ru.sanctio.models.entity.Client;

/**
 * Интерфейс для взаимодействия с базой данных,
 * касающегося работы с данными клиента
 */
public interface DBManagerClient {

    boolean createNewClient(Client newClient, Address newAddress);
    Address update(Address newAddress);

    Client getClientById(String id);
}
