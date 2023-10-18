package ru.sanctio.service;

import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.models.dto.ClientDTO;

public interface ClientService {
    boolean createNewClient(ClientDTO clientDTO, AddressDTO addressDTO);
    boolean updateClient(ClientDTO clientDTO, AddressDTO addressDTO);

    ClientDTO getClientById(String id);
}
