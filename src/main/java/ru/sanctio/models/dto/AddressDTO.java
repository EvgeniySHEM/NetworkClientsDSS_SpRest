package ru.sanctio.models.dto;

import java.io.Serializable;

public record AddressDTO(
        int id,
        String ip,
        String mac,
        String model,
        String address,
        ClientDTO client) implements Serializable {
}
