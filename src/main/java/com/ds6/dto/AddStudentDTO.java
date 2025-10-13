package com.ds6.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record AddStudentDTO(
    @NotNull(message = "Student ID cannot be null")
    UUID studentId
) {
}
