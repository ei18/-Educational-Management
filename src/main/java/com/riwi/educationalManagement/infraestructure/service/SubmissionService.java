package com.riwi.educationalManagement.infraestructure.service;

import com.riwi.educationalManagement.api.dto.request.SubmissionRequest;
import com.riwi.educationalManagement.api.dto.response.CompleteAssignmentInfoResponse;
import com.riwi.educationalManagement.api.dto.response.SubmissionResponse;
import com.riwi.educationalManagement.api.dto.response.UserInfoResponse;
import com.riwi.educationalManagement.domain.entities.Assignment;
import com.riwi.educationalManagement.domain.entities.Submission;
import com.riwi.educationalManagement.domain.entities.User;
import com.riwi.educationalManagement.domain.repositories.AssignmentRepository;
import com.riwi.educationalManagement.domain.repositories.SubmissionRepository;
import com.riwi.educationalManagement.domain.repositories.UserRepository;
import com.riwi.educationalManagement.infraestructure.abstract_service.ISubmissionService;
import com.riwi.educationalManagement.utils.exception.BadRequestException;
import com.riwi.educationalManagement.utils.message.ErrorMessages;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class SubmissionService implements ISubmissionService{
    @Autowired
    private final SubmissionRepository submissionRepository;
    @Autowired
    private final AssignmentRepository assignmentRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public SubmissionResponse create(SubmissionRequest request) {
        Assignment assignment = this.assignmentRepository.findById(request.getAssignmentId()).orElseThrow(() -> new BadRequestException(ErrorMessages.IdNotFound("Assignment")));

        User user = this.userRepository.findById(request.getUserId()).orElseThrow(() -> new BadRequestException(ErrorMessages.IdNotFound("User")));

        Submission submission = this.requestToEntity(request);

        submission.setAssignment(assignment);
        submission.setUser(user);

        return this.entityToResponse(this.submissionRepository.save(submission));
    }

    @Override
    public SubmissionResponse get(Long id) {
        return this.entityToResponse(this.find(id));
    }

    @Override
    public Page<SubmissionResponse> getAll(int page, int size) {
        if (page < 0) page = 0;
        PageRequest pagination = PageRequest.of(page, size);

        return this.submissionRepository.findAll(pagination).map(this::entityToResponse);
    }

    @Override
    public SubmissionResponse update(SubmissionRequest request, Long id) {
        Submission submission = this.find(id);

        Assignment assignment = this.assignmentRepository.findById(request.getAssignmentId()).orElseThrow(() -> new BadRequestException(ErrorMessages.IdNotFound("Assignment")));

        User user = this.userRepository.findById(request.getUserId()).orElseThrow(() -> new BadRequestException(ErrorMessages.IdNotFound("User")));

        submission = this.requestToEntity(request);
        submission.setAssignment(assignment);
        submission.setUser(user);
        submission.setId(id);

        return this.entityToResponse(this.submissionRepository.save(submission));
    }

    @Override
    public void delete(Long id) {
        this.submissionRepository.delete(this.find(id));
    }

    private SubmissionResponse entityToResponse(Submission entity){
        CompleteAssignmentInfoResponse assignment = new CompleteAssignmentInfoResponse();
        BeanUtils.copyProperties(entity.getAssignment(), assignment);

        UserInfoResponse user = new UserInfoResponse();
        BeanUtils.copyProperties(entity.getUser(), user);

        return SubmissionResponse.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .submissionDate(entity.getSubmissionDate())
                .grade(entity.getGrade())
                .assignment(assignment)
                .user(user)
                .build();
    }

    private Submission requestToEntity(SubmissionRequest request){
        return Submission.builder()
                .content(request.getContent())
                .submissionDate(request.getSubmissionDate())
                .grade(request.getGrade())
                .build();
    }

    private Submission find(Long id) {
        return this.submissionRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Assignment"));
    }
}
