package com.ds6.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ds6.dto.AddStudentDTO;
import com.ds6.dto.ClassDTO;
import com.ds6.dto.CreateClassDTO;
import com.ds6.service.ClassService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    // --- Endpoints para CRUD de Turmas ---

    @PostMapping
    public ResponseEntity<ClassDTO> createClass(@Valid @RequestBody CreateClassDTO createClassDTO) {
        ClassDTO createdClass = classService.createClass(createClassDTO);
        return new ResponseEntity<>(createdClass, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClassDTO>> getAllClasses() {
        List<ClassDTO> classes = classService.getAllClasses();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDTO> getClassById(@PathVariable UUID id) {
        ClassDTO classDTO = classService.getClassById(id);
        return ResponseEntity.ok(classDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable UUID id) {
        classService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }

    // --- Endpoints para gerir alunos na turma ---

    @PostMapping("/{classId}/students")
    public ResponseEntity<ClassDTO> addStudentToClass(@PathVariable UUID classId, @Valid @RequestBody AddStudentDTO addStudentDTO) {
        ClassDTO updatedClass = classService.addStudentToClass(classId, addStudentDTO);
        return ResponseEntity.ok(updatedClass);
    }

    @DeleteMapping("/{classId}/students/{studentId}")
    public ResponseEntity<Void> removeStudentFromClass(@PathVariable UUID classId, @PathVariable UUID studentId) {
        classService.removeStudentFromClass(classId, studentId);
        return ResponseEntity.noContent().build();
    }
}
