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


/**
 * Класс для реализации бизнес логики с данными адреса.
 */
@Service
public class AddressServiceImpl implements AddressService {


    private final DBManagerAddress dbManagerAddress;
    private final AddressDTOMapper addressDTOMapper;

    @Autowired
    public AddressServiceImpl(DBManagerAddress dbManagerAddress, AddressDTOMapper addressDTOMapper) {
        this.dbManagerAddress = dbManagerAddress;
        this.addressDTOMapper = addressDTOMapper;
    }

    /**
     * Возвращает информацию о всех данных клиентов, хранящихся в базе данных,
     * отсортированных по идентификатору клиента.
     * @return List<AddressDTO>
     */
    @Override
    @Transactional
    public List<AddressDTO> getSortedData() {
        List<Address> list = dbManagerAddress.getAllInformation();
        list.sort((a, b) -> a.getClient().getClientId() - b.getClient().getClientId());
        return addressDTOMapper.toDtoList(list);
    }

    /**
     * Возвращает данные адреса, хранящиеся в базе данных, по его идентификатору.
     * @param addressId
     * @return AddressDTO
     */
    @Override
    @Transactional
    public AddressDTO selectAddressById(String addressId) {
        Address address = dbManagerAddress.selectAddressById(addressId);
        return addressDTOMapper.toDto(address);
    }

    /**
     * Удаляет данные адреса, хранящиеся в базе данных, по его идентификатору.
     * @param addressId
     */
    @Override
    @Transactional
    public void deleteAddress(String addressId) {
        dbManagerAddress.deleteAddressById(addressId);
    }
}
