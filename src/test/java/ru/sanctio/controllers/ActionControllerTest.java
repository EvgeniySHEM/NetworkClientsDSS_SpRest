package ru.sanctio.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.sanctio.exception_handling.GlobalExceptionHandler;
import ru.sanctio.exception_handling.NoSuchDataException;
import ru.sanctio.exception_handling.RepeatingAddressException;
import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.models.dto.ClientDTO;
import ru.sanctio.service.AddressService;
import ru.sanctio.service.ClientService;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ActionControllerTest {

    @Mock
    private ClientService clientService;
    @Mock
    private AddressService addressService;
    @InjectMocks
    private ActionController actionController;

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private static ClientDTO testClientDTO;
    private static AddressDTO addressDTO;

    @BeforeAll
    static void init() {
        testClientDTO = new ClientDTO(
                1,
                "name",
                "type",
                "added"
        );

        addressDTO =
                new AddressDTO(
                        1,
                        "111.111.111.111",
                        "77-6s-52-7f-ja-4h",
                        "model",
                        "address",
                        testClientDTO
                );
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(actionController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getClientById_ShouldReturnClientDtoById() throws Exception {
        when(clientService.getClientById(any())).thenReturn(testClientDTO);

        mockMvc.perform(get("/api/clients/action/{id}", 1))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.clientId").value(1),
                        jsonPath("$.clientName").value("name"),
                        jsonPath("$.type").value("type"),
                        jsonPath("$.added").value("added")
                );
        verify(clientService, times(1)).getClientById(any());
    }

    @Test
    void getClientById_ShouldReturnNoSuchDataException() throws Exception {
        when(clientService.getClientById(any())).thenReturn(null);

        mockMvc.perform(get("/api/clients/action/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchDataException))
                .andExpect(result -> assertEquals(
                        "There is no client with ID = 1 in database",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
        verify(clientService, times(1)).getClientById(any());
    }

    @Test
    void getAddressById_ShouldReturnAddressDtoById() throws Exception {
        when(addressService.selectAddressById(any())).thenReturn(addressDTO);

        MvcResult mvcResult = mockMvc.perform(get("/api/clients/action/addresses/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        AddressDTO result = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(addressDTO, result);
        verify(addressService, times(1)).selectAddressById(any());
    }

    @Test
    void getAddressById_ShouldReturnNoSuchDataException() throws Exception {
        when(addressService.selectAddressById(any())).thenReturn(null);

        mockMvc.perform(get("/api/clients/action/addresses/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchDataException))
                .andExpect(result -> assertEquals(
                        "There is no address with ID = 1 in database",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));

        verify(addressService, times(1)).selectAddressById(any());
    }

    @Test
    void createNewClient() throws Exception {
        when(clientService.createNewClient(any(), any())).thenReturn(true);
        String addressDtoJson = objectMapper.writeValueAsString(addressDTO);

        mockMvc.perform(post("/api/clients/action/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addressDtoJson))
                .andExpect(status().isCreated());

        verify(clientService, times(1)).createNewClient(any(), any());
    }

    @Test
    void createNewClient_ShouldReturnRepeatingAddressException() throws Exception {
        when(clientService.createNewClient(any(), any())).thenReturn(false);
        String addressDtoJson = objectMapper.writeValueAsString(addressDTO);

        mockMvc.perform(post("/api/clients/action/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addressDtoJson))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RepeatingAddressException))
                .andExpect(result -> assertEquals(
                        "Duplicate addresses in the database",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));

        verify(clientService, times(1)).createNewClient(any(), any());
    }

    @Test
    void update() throws Exception {
        when(clientService.updateClient(any())).thenReturn(addressDTO);
        String addressDtoJson = objectMapper.writeValueAsString(addressDTO);

        MvcResult mvcResult = mockMvc.perform(put("/api/clients/action/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addressDtoJson))
                .andExpect(status().isOk())
                .andReturn();

        AddressDTO result = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(addressDTO, result);
        verify(clientService, times(1)).updateClient(any());
    }

    @Test
    void deleteAddress() throws Exception {
        mockMvc.perform(delete("/api/clients/action/{id}", 1))
                .andExpect(status().isNoContent());

        verify(addressService, times(1)).deleteAddress(any());
    }

}