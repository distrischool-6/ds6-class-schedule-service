package com.ds6.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ds6.model.SchoolClass;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, UUID>{
    
}
