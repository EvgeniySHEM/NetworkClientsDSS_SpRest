package ru.sanctio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sanctio.dao.DBManagerClient;
import ru.sanctio.models.entity.Address;
import ru.sanctio.models.entity.Client;
import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.models.dto.ClientDTO;
import ru.sanctio.models.maper.AddressDTOMapper;
import ru.sanctio.models.maper.ClientDTOMapper;
import ru.sanctio.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {


    private final DBManagerClient dbManagerClient;
    private final AddressDTOMapper addressDTOMapper;
    private final ClientDTOMapper clientDTOMapper;


    @Autowired
    public ClientServiceImpl(DBManagerClient dbManagerClient, AddressDTOMapper addressDTOMapper, ClientDTOMapper clientDTOMapper) {
        this.dbManagerClient = dbManagerClient;
        this.addressDTOMapper = addressDTOMapper;
        this.clientDTOMapper = clientDTOMapper;
    }

    @Override
    @Transactional
    public boolean createNewClient(ClientDTO clientDTO, AddressDTO addressDTO) {
        Client newClient = clientDTOMapper.toEntity(clientDTO);
        Address newAddress = addressDTOMapper.toEntity(addressDTO);

        return dbManagerClient.createNewClient(newClient, newAddress);
    }

    @Override
    @Transactional
    public AddressDTO updateClient(AddressDTO addressDTO) {
        Address newAddress = addressDTOMapper.toEntity(addressDTO);
        Address address = dbManagerClient.update(newAddress);
        return addressDTOMapper.toDto(address);
    }

    @Override
    @Transactional
    public ClientDTO getClientById(String id) {
        Client client = dbManagerClient.getClientById(id);
        return clientDTOMapper.toDto(client);
    }
}
