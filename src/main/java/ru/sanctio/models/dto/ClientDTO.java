package ru.sanctio.models.dto;

import java.io.Serializable;

public record ClientDTO(int clientId,
                        String clientName,
                        String type,
                        String added) implements Serializable {
}
