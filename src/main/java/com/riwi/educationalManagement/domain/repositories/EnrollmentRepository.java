package com.riwi.educationalManagement.domain.repositories;

import com.riwi.educationalManagement.domain.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{
    List<Enrollment> findAllByUserId(Long userId);

    List<Enrollment> findAllByCourseId(Long courseId);
}
