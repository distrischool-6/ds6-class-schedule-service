package com.ds6.service;

import java.util.List;
import java.util.UUID;

import com.ds6.dto.AddStudentDTO;
import com.ds6.dto.ClassDTO;
import com.ds6.dto.CreateClassDTO;

public interface ClassService {
    public ClassDTO createClass(CreateClassDTO createClassDTO);
    public ClassDTO getClassById(UUID id);
    public List<ClassDTO> getAllClasses();
    public void deleteClass(UUID id);
    
    public ClassDTO addStudentToClass(UUID classId, AddStudentDTO addStudentDTO);
    public void removeStudentFromClass(UUID classId, UUID studentId);
}
