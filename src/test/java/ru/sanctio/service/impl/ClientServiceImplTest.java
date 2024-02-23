package ru.sanctio.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sanctio.dao.DBManagerClient;
import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.models.dto.ClientDTO;
import ru.sanctio.models.maper.AddressDTOMapper;
import ru.sanctio.models.maper.ClientDTOMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientServiceImpl;
    @Mock
    private DBManagerClient dbManagerClient;
    @Mock
    private AddressDTOMapper addressDTOMapper;
    @Mock
    private ClientDTOMapper clientDTOMapper;

    private ClientDTO client;
    private AddressDTO address;

    @BeforeEach
    void create() {
        client = new ClientDTO(1, "Макс", "Физическое лицо", "2022-02-01");
        address = new AddressDTO(1, "111.111.111.111", "dd-dd-dd-dd-dd-dd",
                "model", "address", client);
    }

    @Test
    void createNewClient() {
        clientServiceImpl.createNewClient(client, address);

        verify(clientDTOMapper, times(1)).toEntity(any());
        verify(addressDTOMapper, times(1)).toEntity(any());
        verify(dbManagerClient, times(1)).createNewClient(any(), any());
    }

    @Test
    void updateClient() {
        clientServiceImpl.updateClient(address);

        verify(addressDTOMapper, times(1)).toEntity(any());
        verify(dbManagerClient, times(1)).update(any());
        verify(addressDTOMapper, times(1)).toDto(any());
    }

    @Test
    void getClientById() {
        clientServiceImpl.getClientById("1");

        verify(dbManagerClient, times(1)).getClientById(any());
        verify(clientDTOMapper, times(1)).toDto(any());
    }

}