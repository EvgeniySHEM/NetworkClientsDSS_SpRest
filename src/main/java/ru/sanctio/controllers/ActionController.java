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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO getClientById(@PathVariable("id") String id) {
        ClientDTO client = clientService.getClientById(id);

        if (client == null) {
            throw new NoSuchDataException("There is no client with ID = " + id + " in database");
        }
        return client;
    }

    @GetMapping("/addresses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO getAddressById(@PathVariable("id") String id) {
        AddressDTO addressDTO = addressService.selectAddressById(id);

        if (addressDTO == null) {
            throw new NoSuchDataException("There is no address with ID = " + id + " in database");
        }
        return addressDTO;
    }

    @PostMapping("/")
    public ResponseEntity<String> createNewClient(@RequestBody AddressDTO addressDTO) {
        ClientDTO clientDTO = addressDTO.client();
        boolean newClient = clientService.createNewClient(clientDTO, addressDTO);

        if (!newClient) {
            throw new RepeatingAddressException("Duplicate addresses in the database");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO update(@RequestBody AddressDTO addressDTO) {

        return clientService.updateClient(addressDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable("id") String id) {
        addressService.deleteAddress(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
