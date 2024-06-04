package com.riwi.educationalManagement.infraestructure.abstract_service;

import java.util.List;

import com.riwi.educationalManagement.api.dto.request.CourseRequest;
import com.riwi.educationalManagement.api.dto.response.CourseToUserResponse;
import com.riwi.educationalManagement.domain.entities.Course;

public interface ICourseService extends CrudServices<CourseRequest, CourseToUserResponse, Long>{
    List<Course> findAllByCourseName(String courseName);
}
