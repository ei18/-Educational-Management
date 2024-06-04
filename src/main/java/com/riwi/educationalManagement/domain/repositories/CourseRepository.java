package com.riwi.educationalManagement.domain.repositories;

import com.riwi.educationalManagement.domain.entities.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    //Solo devuelve los datos que hay en parámetros <>
    List<Course> findAllByCourseNameLike(String courseName);
}
