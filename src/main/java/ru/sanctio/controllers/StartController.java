package ru.sanctio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.service.AddressService;
import ru.sanctio.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StartController {

    private final UserService userService;
    private final AddressService addressService;

    @Autowired
    public StartController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @GetMapping("/clients")
    public List<AddressDTO> showAllInformation() {
        return addressService.getSortedData();
    }
}
