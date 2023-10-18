package ru.sanctio.dao;

import ru.sanctio.models.entity.Address;

import java.util.List;

public interface DBManagerAddress {
    List<Address> getAllInformation();

    Address selectAddressById(String addressId);

    void deleteAddressById(String addressId);
}
