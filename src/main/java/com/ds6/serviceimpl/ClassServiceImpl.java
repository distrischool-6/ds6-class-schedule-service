package com.ds6.serviceimpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds6.dto.AddStudentDTO;
import com.ds6.dto.ClassDTO;
import com.ds6.dto.CreateClassDTO;
import com.ds6.exception.ResourceNotFoundException;
import com.ds6.model.SchoolClass;
import com.ds6.repository.SchoolClassRepository;
import com.ds6.service.ClassService;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final SchoolClassRepository classRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Transactional
    public ClassDTO createClass(CreateClassDTO dto) {
        SchoolClass newClass = new SchoolClass();
        newClass.setId(UUID.randomUUID());
        newClass.setName(dto.name());
        newClass.setShift(dto.shift());
        newClass.setTeacherId(dto.teacherId());

        SchoolClass savedClass = classRepository.save(newClass);
        return toDTO(savedClass);
    }

    @Override
    @Transactional(readOnly = true)
    public ClassDTO getClassById(UUID id) {
        SchoolClass schoolClass = findClassById(id);
        return toDTO(schoolClass);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassDTO> getAllClasses() {
        return classRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public void deleteClass(UUID id) {
        if (!classRepository.existsById(id)) {
            throw new ResourceNotFoundException("Class not found with id: " + id);
        }
        classRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ClassDTO addStudentToClass(UUID classId, AddStudentDTO dto) {
        SchoolClass schoolClass = findClassById(classId);
        schoolClass.getStudentIds().add(dto.studentId());
        
        SchoolClass updatedClass = classRepository.save(schoolClass);

        publishScheduleUpdatedEvent(updatedClass.getId());

        return toDTO(updatedClass);
    }

    @Override
    @Transactional
    public void removeStudentFromClass(UUID classId, UUID studentId) {
        SchoolClass schoolClass = findClassById(classId);
        schoolClass.getStudentIds().remove(studentId);
        classRepository.save(schoolClass);

        publishScheduleUpdatedEvent(schoolClass.getId());
    }

    // --- Métodos utilitários ---
    private SchoolClass findClassById(UUID id) {
        return classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));
    }

    private ClassDTO toDTO(SchoolClass schoolClass) {
        return new ClassDTO(
            schoolClass.getId(),
            schoolClass.getName(),
            schoolClass.getShift(),
            schoolClass.getTeacherId(),
            schoolClass.getStudentIds()
        );
    }

    private void publishScheduleUpdatedEvent(UUID classId) {
        try {
            String topic = "schedule.updated";
            String payload = classId.toString();

            kafkaTemplate.send(topic, payload);
            log.info("Event 'schedule.updated' published for class ID: {}", payload);
        } catch (Exception e) {
            log.error("Failed to publish 'schedule.updated' event for class ID: {}. Error: {}", classId, e.getMessage());
        }
    }
}