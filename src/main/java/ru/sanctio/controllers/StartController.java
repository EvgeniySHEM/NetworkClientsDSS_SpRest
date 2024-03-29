package ru.sanctio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.service.AddressService;

import java.util.List;

/**
 * Контроллер для получения всех данных клиентов, хранящихся в базе данных.
 */
@RestController
@RequestMapping("/api")
public class StartController {

    private final AddressService addressService;

    @Autowired
    public StartController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * Возвращает информацию о всех данных клиентов, хранящихся в базе данных.
     * @return List<AddressDTO>, HttpStatus.OK
     */
    @GetMapping("/clients")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDTO> showAllInformation() {
        return addressService.getSortedData();
    }
}
