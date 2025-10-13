package com.ds6.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "classes")
@Data
@NoArgsConstructor
public class SchoolClass {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String shift;

    // ID do professor responsável por esta turma.
    // Esta é uma referência ao ID do professor no teacher-service.
    @Column(name = "teacher_id", nullable = false)
    private UUID teacherId;

    // IDs dos alunos matriculados nesta turma.
    // Esta é uma coleção de referências aos IDs dos alunos no student-service.
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "class_students", joinColumns = @JoinColumn(name = "class_id"))
    @Column(name = "student_id", nullable = false)
    private Set<UUID> studentIds = new HashSet<>();
}
