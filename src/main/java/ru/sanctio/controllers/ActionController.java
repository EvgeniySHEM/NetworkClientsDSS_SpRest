package ru.sanctio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sanctio.exception_handling.NoSuchDataException;
import ru.sanctio.exception_handling.RepeatingAddressException;
import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.models.dto.ClientDTO;
import ru.sanctio.service.AddressService;
import ru.sanctio.service.ClientService;

/**
 * Контроллер для взаимодействия с отдельными данными клиентов, хранящихся в базе данных.
 * Доступен для обращения пользователю с ролью: "MANAGER"
 */
@RestController
@RequestMapping("/api/clients/action")
public class ActionController {

    private final ClientService clientService;
    private final AddressService addressService;

    @Autowired
    public ActionController(ClientService clientService, AddressService addressService) {
        this.clientService = clientService;
        this.addressService = addressService;
    }


    /**
     * Возвращает сведения, имеющиеся в базе данных, о клиенте по его идентификатору
     * @param id
     * @return ClientDTO, HttpStatus.OK
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO getClientById(@PathVariable("id") String id) {
        ClientDTO client = clientService.getClientById(id);

        if (client == null) {
            throw new NoSuchDataException("There is no client with ID = " + id + " in database");
        }
        return client;
    }

    /**
     * Возвращает сведения, имеющиеся в базе данных, об адресе
     * @param id
     * @return AddressDTO, HttpStatus.OK
     */
    @GetMapping("/addresses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO getAddressById(@PathVariable("id") String id) {
        AddressDTO addressDTO = addressService.selectAddressById(id);

        if (addressDTO == null) {
            throw new NoSuchDataException("There is no address with ID = " + id + " in database");
        }
        return addressDTO;
    }

    /**
     * Сохраняет данные о клиенте в базе данных
     * @param addressDTO
     * @throws RepeatingAddressException, если клиент с таким адресом уже зарегистрирован
     * @return HttpStatus.CREATED, если сохранение данных прошло успешно,
     * HttpStatus.CONFLICT - если клиент с таким адресом уже зарегистрирован.
     */
    @PostMapping("/")
    public ResponseEntity<String> createNewClient(@RequestBody AddressDTO addressDTO) {
        ClientDTO clientDTO = addressDTO.client();
        boolean newClient = clientService.createNewClient(clientDTO, addressDTO);

        if (!newClient) {
            throw new RepeatingAddressException("Duplicate addresses in the database");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Изменяет данные в базе данных
     * @param addressDTO - с данными, которые нужно изменить
     * @return AddressDTO - с измененными данными, HttpStatus.OK
     */
    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO update(@RequestBody AddressDTO addressDTO) {

        return clientService.updateClient(addressDTO);
    }


    /**
     * Удаляет адрес из базы данных
     * @param id - адреса, который нужно удалить
     * @return HttpStatus.NO_CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable("id") String id) {
        addressService.deleteAddress(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
