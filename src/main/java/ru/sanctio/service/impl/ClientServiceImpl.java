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


    private DBManagerClient dbManagerClient;

    @Autowired
    public ClientServiceImpl(DBManagerClient dbManagerClient) {
        this.dbManagerClient = dbManagerClient;
    }

    @Override
    @Transactional
    public boolean createNewClient(ClientDTO clientDTO, AddressDTO addressDTO) {
        Client newClient = ClientDTOMapper.INSTANCE.mapToEntity(clientDTO);
        Address newAddress = AddressDTOMapper.INSTANCE.mapToEntity(addressDTO);

        return dbManagerClient.createNewClient(newClient, newAddress);
    }

    @Override
    @Transactional
    public AddressDTO updateClient(AddressDTO addressDTO) {
        Address newAddress = AddressDTOMapper.INSTANCE.mapToEntity(addressDTO);
        Address address = dbManagerClient.update(newAddress);
        return AddressDTOMapper.INSTANCE.mapToDto(address);
    }

    @Override
    @Transactional
    public ClientDTO getClientById(String id) {
        Client client = dbManagerClient.getClientById(id);
        return ClientDTOMapper.INSTANCE.mapToDto(client);
    }
}
