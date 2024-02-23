package ru.sanctio.dao.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.sanctio.TestConfig;
import ru.sanctio.dao.DBManagerAddress;
import ru.sanctio.dao.DBManagerClient;
import ru.sanctio.models.entity.Address;
import ru.sanctio.models.entity.Client;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
class DBManagerClientImplTest {

    @Autowired
    private DBManagerClient dbManagerClient;
    @Autowired
    private DBManagerAddress dbManagerAddress;

    private Client client;
    private Address address;
    private Client client2;
    private Address address2;


    @Test
    public void testCreateNewClient() {
        client = new Client("Паша", "Физическое лицо", "2023-05-13");
        address = new Address("111.111.111.111", "dd-dd-dd-dd-dd-dd", "model1",
                "Minsk");
        assertTrue(dbManagerClient.createNewClient(client, address));

        client2 = new Client("Ваня", "Физическое лицо", "2023-05-13");
        address2 = new Address("222.222.222.222", "ff-ff-ff-ff-ff-ff", "model2",
                "Moscow");
        assertTrue(dbManagerClient.createNewClient(client2, address2));
    }

    @Test
    void createNewClient_ShouldReturnFalseIfAddressAlreadyExists() {

        client = new Client("Паша", "Физическое лицо", "2023-05-13");
        address = new Address("111.111.111.111", "dd-dd-dd-dd-dd-dd", "model1",
                "Minsk");
        dbManagerClient.createNewClient(client, address);

        client2 = new Client("Ваня", "Физическое лицо", "2023-05-13");
        address2 = new Address("111.111.111.111", "dd-dd-dd-dd-dd-dd", "model1",
                "Minsk");
        assertFalse(dbManagerClient.createNewClient(client2, address2));
    }

    @Test
    void createNewClient_ShouldAddAdditionalAddress() {

        client = new Client("Паша", "Физическое лицо", "2023-05-13");
        address = new Address("111.111.111.111", "dd-dd-dd-dd-dd-dd", "model1",
                "Minsk");
        dbManagerClient.createNewClient(client, address);

        client2 = new Client("Паша", "Физическое лицо", "2023-05-13");
        address2 = new Address("222.222.222.222", "dd-dd-dd-dd-dd-dd", "model2",
                "Minsk");

        assertTrue(dbManagerClient.createNewClient(client2, address2));

        List<Address> allInformation = dbManagerAddress.getAllInformation();

        assertEquals(allInformation.get(0).getClient().getClientId(),
                allInformation.get(1).getClient().getClientId());

    }

    @Test
    void update_UpdateClient() {
        client = new Client("Паша", "Физическое лицо", "2023-05-13");
        address = new Address("111.111.111.111", "dd-dd-dd-dd-dd-dd", "model1",
                "Minsk");
        dbManagerClient.createNewClient(client, address);

        client = address.getClient();
        client.setClientName("Макс");
        client.setType("Физическое лицо");
        client.setAdded("2022-01-10");

        dbManagerClient.update(address);

        Address address1 = dbManagerAddress.selectAddressById(String.valueOf(address.getId()));

        assertEquals(client, address1.getClient());
    }

    @Test
    void update_UpdateAddress() {
        client = new Client("Паша", "Физическое лицо", "2023-05-13");
        address = new Address("111.111.111.111", "dd-dd-dd-dd-dd-dd", "model1",
                "Minsk");
        dbManagerClient.createNewClient(client, address);

        address.setIp("111.111.111.222");
        address.setMac("ss-ss-ss-ss-ss-ss");
        address.setModel("model2");
        address.setAddress("Kiev");

        Address update = dbManagerClient.update(address);

        assertEquals(address, update);
    }

    @Test
    void getClientById() {
        client = new Client("Паша", "Физическое лицо", "2023-05-13");
        address = new Address("111.111.111.111", "dd-dd-dd-dd-dd-dd", "model1",
                "Minsk");
        dbManagerClient.createNewClient(client, address);

        int clientId = address.getClient().getClientId();

        Client clientById = dbManagerClient.getClientById(String.valueOf(clientId));

        assertEquals(address.getClient(), clientById);
    }

    @Test
    void getClientById_ShouldReturnNullIfClientNotExist() {
        client = new Client("Паша", "Физическое лицо", "2023-05-13");
        address = new Address("111.111.111.111", "dd-dd-dd-dd-dd-dd", "model1",
                "Minsk");
        dbManagerClient.createNewClient(client, address);
        List<Address> allInformation = dbManagerAddress.getAllInformation();

        Client clientById = dbManagerClient.getClientById(String.valueOf(allInformation.size() + 1));

        assertNull(clientById);
    }

}