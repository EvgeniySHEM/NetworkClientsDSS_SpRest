package ru.sanctio.models.maper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sanctio.models.dto.ClientDTO;
import ru.sanctio.models.entity.Client;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientDTOMapperTest {

    private ClientDTOMapper clientDTOMapper;

    @BeforeEach
    void setUp() {
        clientDTOMapper = new ClientDTOMapperImpl();
    }

    @Test
    void mapToEntity() {
        ClientDTO clientDTO = new ClientDTO(0, "Миша", "Юридическое лицо", "2023-09-07");

        Client client = clientDTOMapper.toEntity(clientDTO);

        assertEquals(clientDTO.clientId(), client.getClientId());
        assertEquals(clientDTO.clientName(), client.getClientName());
        assertEquals(clientDTO.type(), client.getType());
        assertEquals(clientDTO.added(), client.getAdded());
    }

    @Test
    void mapToEntity_ShouldReturnNull() {
        ClientDTO clientDTO = null;

        Client client = clientDTOMapper.toEntity(clientDTO);

        assertNull(client);
    }

    @Test
    void mapToDto() {
        Client client = new Client(0, "Миша", "Юридическое лицо", "2023-09-07");

        ClientDTO clientDTO = clientDTOMapper.toDto(client);

        assertEquals(client.getClientId(), clientDTO.clientId());
        assertEquals(client.getClientName(), clientDTO.clientName());
        assertEquals(client.getType(), clientDTO.type());
        assertEquals(client.getAdded(), clientDTO.added());
    }

    @Test
    void mapToDto_ShouldReturnNull() {
        Client client = null;

        ClientDTO clientDTO = clientDTOMapper.toDto(client);

        assertNull(clientDTO);
    }

    @Test
    void toDtoList() {
        List<Client> clients =
                List.of(
                        new Client(0, "Миша", "Юридическое лицо", "2023-09-07"));

        List<ClientDTO> dtoList = clientDTOMapper.toDtoList(clients);

        assertEquals(clients.get(0).getClientId(), dtoList.get(0).clientId());
        assertEquals(clients.get(0).getClientName(), dtoList.get(0).clientName());
        assertEquals(clients.get(0).getType(), dtoList.get(0).type());
        assertEquals(clients.get(0).getAdded(), dtoList.get(0).added());
    }

    @Test
    void toDtoList_ShouldReturnNull() {
        List<Client> clients = null;

        List<ClientDTO> dtoList = clientDTOMapper.toDtoList(clients);

        assertNull(dtoList);
    }

}