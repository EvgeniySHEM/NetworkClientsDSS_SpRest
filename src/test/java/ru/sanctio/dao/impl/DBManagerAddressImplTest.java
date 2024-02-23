package ru.sanctio.dao.impl;

import org.junit.jupiter.api.BeforeEach;
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
class DBManagerAddressImplTest {

    @Autowired
    private DBManagerClient dbManagerClient;
    @Autowired
    private DBManagerAddress dbManagerAddress;

    private Client client;
    private Address address;
    private Client client2;
    private Address address2;

    @BeforeEach
    void create() {
        client = new Client("Паша", "Физическое лицо", "2023-05-13");
        address = new Address("111.111.111.111", "dd-dd-dd-dd-dd-dd", "model1",
                "Minsk");
        dbManagerClient.createNewClient(client, address);

        client2 = new Client("Ваня", "Физическое лицо", "2023-05-13");
        address2 = new Address("222.222.222.222", "ff-ff-ff-ff-ff-ff", "model2",
                "Moscow");
        dbManagerClient.createNewClient(client2, address2);
    }

    @Test
    void getAllInformation() {
        List<Address> allInformation = dbManagerAddress.getAllInformation();

        assertEquals(address, allInformation.get(0));
        assertEquals(address2, allInformation.get(1));
        assertEquals(2, allInformation.size());
    }

    @Test
    void selectAddressById() {
        Address address1 = dbManagerAddress.selectAddressById(String.valueOf(address.getId()));

        assertEquals(address, address1);
    }

    @Test
    void deleteAddressById() {
        dbManagerAddress.deleteAddressById(String.valueOf(address2.getId()));
        List<Address> allInformation = dbManagerAddress.getAllInformation();

        assertEquals(1, allInformation.size());
        assertEquals(address.getId(), allInformation.get(0).getId());
    }

    @Test
    void deleteAddressById_ShouldNotDeleteAnyElement() {
        dbManagerAddress.deleteAddressById("3");
        List<Address> allInformation = dbManagerAddress.getAllInformation();

        assertEquals(2, allInformation.size());
    }

}