package ru.sanctio.dao;

import ru.sanctio.models.entity.Address;
import ru.sanctio.models.entity.Client;

public interface DBManagerClient {

    boolean createNewClient(Client newClient, Address newAddress);
    boolean update(Client newClient, Address newAddress);

    Client getClientById(String id);
}
