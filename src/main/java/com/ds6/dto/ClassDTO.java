package com.ds6.dto;

import java.util.Set;
import java.util.UUID;

public record ClassDTO(
    UUID id,
    String name,
    String shift,
    UUID teacherId,
    Set<UUID> studentIds
) {
    
}
