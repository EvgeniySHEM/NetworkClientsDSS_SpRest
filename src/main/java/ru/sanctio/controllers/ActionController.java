package ru.sanctio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sanctio.exception_handling.IncorrectData;
import ru.sanctio.exception_handling.NoSuchDataException;
import ru.sanctio.exception_handling.RepeatingAddressException;
import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.models.dto.ClientDTO;
import ru.sanctio.service.AddressService;
import ru.sanctio.service.ClientService;

@RestController
@RequestMapping("api/clients/action")
public class ActionController {

    private final ClientService clientService;
    private final AddressService addressService;

    @Autowired
    public ActionController(ClientService clientService, AddressService addressService) {
        this.clientService = clientService;
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable String id) {
        ClientDTO client = clientService.getClientById(id);

        if(client == null) {
            throw new NoSuchDataException("There is no client with ID = " + id + " in database");
        }
        return client;
    }

    @GetMapping("/addresses/{id}")
    public AddressDTO getAddressById(@PathVariable String id) {
        AddressDTO addressDTO = addressService.selectAddressById(id);

        if(addressDTO == null) {
            throw new NoSuchDataException("There is no address with ID = " + id + " in database");
        }
        return addressDTO;
    }

//    @RequestMapping("/registration")
//    public String showRegistrationJSP() {
//        return "Registration";
//    }
//
    @PostMapping("/")
    public ResponseEntity createNewClient(@RequestBody AddressDTO addressDTO) {

        ClientDTO clientDTO = addressDTO.client();
        boolean newClient = clientService.createNewClient(clientDTO, addressDTO);


        if (!newClient) {
            throw new RepeatingAddressException("Duplicate addresses in the database");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @RequestMapping("/getInfoForUpdate")
//    public String getInfoForUpdate(@RequestParam String addressId, Model model) {
//        AddressDTO addressDTO = addressService.selectAddressById(addressId);
//        model.addAttribute("address", addressDTO);
//
//        return "UpdateClient";
//    }
//
//    @RequestMapping("/update")
//    public String update(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        ClientDTO clientDTO = new ClientDTO(Integer.parseInt(request.getParameter("clientId")),
//                request.getParameter("clientName"),
//                request.getParameter("type"),
//                request.getParameter("date"));
//
//        AddressDTO addressDTO = new AddressDTO(Integer.parseInt(request.getParameter("addressId")),
//                request.getParameter("ip"),
//                request.getParameter("mac"),
//                request.getParameter("model"),
//                request.getParameter("address"),
//                clientDTO);
//
//        boolean newClient = false;
//        try {
//            newClient = clientService.updateClient(clientDTO, addressDTO);
//        } catch (NullPointerException | IllegalArgumentException ex) {
//            response.sendError(490, ex.getMessage());
//        }
//
//        if (newClient) {
//            return "redirect:/allInformation";
//        } else {
//            return "AddNewClientError";
//        }
//    }
//
//    @RequestMapping("/deleteAddress")
//    public String deleteAddress(@RequestParam String addressId) {
//        addressService.deleteAddress(addressId);
//
//        return "redirect:/allInformation";
//    }
}
