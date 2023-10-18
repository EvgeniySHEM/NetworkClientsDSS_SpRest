package ru.sanctio.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.service.AddressService;
import ru.sanctio.service.UserService;


import java.io.IOException;
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
    public List<AddressDTO> showAllInformation(Model model) {
        return addressService.getSortedData();
    }
//
//
//    @RequestMapping("/")
//    public String showAuthorizationView() {
//        return "authorization";
//    }
//
//    @RequestMapping("/authorization")
//    public String authorization(@RequestParam(name = "username") String username,
//                                @RequestParam(name = "password") String password,
//                                HttpServletResponse response) throws IOException {
//        boolean check = userService.checkUser(username, password);
//        if (check) {
//            return "redirect:/allInformation";
//        } else {
//            response.sendError(401, "В доступе отказано : логин или пароль не найдены");
//            return "authorization";
//        }
//    }
//
}
