package ru.sanctio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sanctio.dao.DBManagerAddress;
import ru.sanctio.models.entity.Address;
import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.models.maper.AddressDTOMapper;
import ru.sanctio.service.AddressService;

import java.util.List;


@Service
public class AddressServiceImpl implements AddressService {


    private final DBManagerAddress dbManagerAddress;
    private final AddressDTOMapper addressDTOMapper;

    @Autowired
    public AddressServiceImpl(DBManagerAddress dbManagerAddress, AddressDTOMapper addressDTOMapper) {
        this.dbManagerAddress = dbManagerAddress;
        this.addressDTOMapper = addressDTOMapper;
    }

    @Override
    @Transactional
    public List<AddressDTO> getSortedData() {
        List<Address> list = dbManagerAddress.getAllInformation();
        list.sort((a, b) -> a.getClient().getClientId() - b.getClient().getClientId());
        return addressDTOMapper.toDtoList(list);
    }

    @Override
    @Transactional
    public AddressDTO selectAddressById(String addressId) {
        Address address = dbManagerAddress.selectAddressById(addressId);
        return addressDTOMapper.toDto(address);
    }

    @Override
    @Transactional
    public void deleteAddress(String addressId) {
        dbManagerAddress.deleteAddressById(addressId);
    }
}
