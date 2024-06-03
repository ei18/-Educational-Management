package com.riwi.educationalManagement.infraestructure.service;

import com.riwi.educationalManagement.api.dto.request.EnrollmentRequest;
import com.riwi.educationalManagement.api.dto.response.EnrollmentResponse;
import com.riwi.educationalManagement.domain.entities.Course;
import com.riwi.educationalManagement.domain.entities.Enrollment;
import com.riwi.educationalManagement.domain.entities.User;
import com.riwi.educationalManagement.domain.repositories.EnrollmentRepository;
import com.riwi.educationalManagement.domain.repositories.UserRepository;
import com.riwi.educationalManagement.infraestructure.abstract_service.IEnrollmentService;
import com.riwi.educationalManagement.utils.enums.Role;
import com.riwi.educationalManagement.utils.exception.BadRequestException;
import com.riwi.educationalManagement.utils.message.ErrorMessages;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class EnrollmentService implements IEnrollmentService{

    @Autowired
    private final EnrollmentRepository enrollmentRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public EnrollmentResponse create(EnrollmentRequest request) {
        User user = this.userRepository.findById(request.getUserId()).orElseThrow(() -> new BadRequestException(ErrorMessages.IdNotFound("User")));

        if (Role.INSTRUCTOR.name().equals(user.getRole().name())) {
            throw new BadRequestException(ErrorMessages.RequiredRoleStudent);
        }

        return this.entityToResponse(this.enrollmentRepository.save(this.requestToEntity(request)));
    }

    @Override
    public EnrollmentResponse get(Long id) {
        return this.entityToResponse(this.find(id));
    }

    @Override
    public Page<EnrollmentResponse> getAll(int page, int size) {
        if (page < 0) page = 0;
        PageRequest pagination = PageRequest.of(page, size);

        return this.enrollmentRepository.findAll(pagination).map(this::entityToResponse);
    }

    @Override
    public EnrollmentResponse update(EnrollmentRequest request, Long id) {
        Enrollment enrollment = this.find(id);
        Enrollment enrollmentUpdate = this.requestToEntity(request);
        enrollmentUpdate.setId(id);

        return this.entityToResponse(this.enrollmentRepository.save(enrollmentUpdate));
    }

    @Override
    public void delete(Long id) {
        this.enrollmentRepository.delete(this.find(id));
    }

    private EnrollmentResponse entityToResponse(Enrollment entity){
        return EnrollmentResponse.builder()
                .id(entity.getId())
                .enrollmentDate(entity.getEnrollmentDate())
                .userId(entity.getUser().getId())
                .courseId(entity.getCourse().getId())
                .build();
    }

    private Enrollment requestToEntity(EnrollmentRequest request){
        return Enrollment.builder()
                .course(Course.builder()
                        .id(request.getCourseId())
                        .build())
                .user(User.builder()
                        .id(request.getUserId())
                        .build())
                .enrollmentDate(LocalDateTime.now())
                .build();                   
    }

    private Enrollment find(Long id){
        return this.enrollmentRepository.findById(id).orElseThrow(() -> new BadRequestException(ErrorMessages.IdNotFound("Enrollment")));
    }
}
