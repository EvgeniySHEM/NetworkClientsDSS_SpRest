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
import ru.sanctio.models.dto.AddressDTO;
import ru.sanctio.models.dto.ClientDTO;
import ru.sanctio.service.AddressService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StartControllerTest {

    @InjectMocks
    private StartController startController;

    @Mock
    private AddressService addressService;

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private static List<AddressDTO> testList;

    @BeforeAll
    static void init() {
        ClientDTO testClientDTO = new ClientDTO(
                1,
                "name",
                "type",
                "added"
        );

        testList = new ArrayList<>(List.of(
                new AddressDTO(
                        1,
                        "111.111.111.111",
                        "77-6s-52-7f-ja-4h",
                        "model",
                        "address",
                        testClientDTO))
        );
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(startController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void showAllInformation() throws Exception {
        when(addressService.getSortedData()).thenReturn(testList);

        MvcResult mvcResult = mockMvc.perform(get("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<AddressDTO> list = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        assertEquals(testList, list);
        verify(addressService, times(1)).getSortedData();
    }
}