package ru.sanctio.service;

import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.models.dto.ClientDTO;

public interface ClientService {
    boolean createNewClient(ClientDTO clientDTO, AddressDTO addressDTO);
    AddressDTO updateClient(AddressDTO addressDTO);

    ClientDTO getClientById(String id);
}
