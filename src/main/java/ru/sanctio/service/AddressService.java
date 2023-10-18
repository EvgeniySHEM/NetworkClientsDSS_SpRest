package ru.sanctio.service;

import ru.sanctio.models.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getSortedData();

    AddressDTO selectAddressById(String addressId);

    void deleteAddress(String addressId);
}
