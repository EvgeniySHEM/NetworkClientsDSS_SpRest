package ru.sanctio.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sanctio.dao.DBManagerAddress;
import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.models.entity.Address;
import ru.sanctio.models.entity.Client;
import ru.sanctio.models.maper.AddressDTOMapper;
import ru.sanctio.models.maper.AddressDTOMapperImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    private Address address;
    private Address address2;

    @InjectMocks
    private AddressServiceImpl addressService;
    @Mock
    private DBManagerAddress dbManagerAddress;
    @Spy
    private static AddressDTOMapper addressDTOMapper = new AddressDTOMapperImpl();


    @BeforeEach
    void create() {
        Client client = new Client(1, "Макс", "Физическое лицо", "2022-02-01");
        address = new Address(1, "111.111.111.111", "dd-dd-dd-dd-dd-dd",
                "model", "address", client);

        Client client2 = new Client(2, "Паша", "Физическое лицо", "2022-10-13");
        address2 = new Address(2, "222.222.222.222", "dd-tt-dd-tt-dd-dd",
                "model10", "address10", client2);
    }

    @Test
    void getSortedData() {
        List<Address> list = new ArrayList<>();
        list.add(address2);
        list.add(address);
        when(dbManagerAddress.getAllInformation()).thenReturn(list);

        List<AddressDTO> sortedData = addressService.getSortedData();

        verify(dbManagerAddress, times(1)).getAllInformation();
        verify(addressDTOMapper, times(1)).toDtoList(any());
        assertNotNull(sortedData);
        assertEquals(address.getClient().getClientId(), sortedData.get(0).client().clientId());
    }

    @Test
    void selectAddressById() {
        when(dbManagerAddress.selectAddressById(any())).thenReturn(address);

        AddressDTO addressDTO = addressService.selectAddressById("1");

        verify(dbManagerAddress, times(1)).selectAddressById(any());
        verify(addressDTOMapper, times(1)).toDto(any());
        assertNotNull(addressDTO);
        assertEquals(addressDTO.id(), address.getId());
    }

    @Test
    void deleteAddress() {
        addressService.deleteAddress("1");

        verify(dbManagerAddress, times(1))
                .deleteAddressById(any());
    }

}