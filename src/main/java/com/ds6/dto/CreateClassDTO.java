package com.ds6.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateClassDTO(
    @NotBlank(message = "Name cannot be blank")
    String name,
    
    @NotBlank(message = "Shift cannot be blank")
    String shift,

    @NotNull(message = "Teacher ID cannot be null")
    UUID teacherId
    ) {
}
