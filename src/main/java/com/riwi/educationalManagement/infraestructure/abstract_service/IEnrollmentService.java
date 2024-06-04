package com.riwi.educationalManagement.infraestructure.abstract_service;

import com.riwi.educationalManagement.api.dto.request.EnrollmentRequest;
import com.riwi.educationalManagement.api.dto.response.CourseAndUsersResponse;
import com.riwi.educationalManagement.api.dto.response.EnrollmentResponse;
import com.riwi.educationalManagement.api.dto.response.UserAndCourseResponse;

public interface IEnrollmentService extends CrudServices<EnrollmentRequest, EnrollmentResponse, Long>{
    UserAndCourseResponse getCoursesByIdUser(Long userId);

    CourseAndUsersResponse getAllUserByCourseId(Long courseId);
}
