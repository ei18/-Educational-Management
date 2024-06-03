package com.riwi.educationalManagement.infraestructure.abstract_service;

import com.riwi.educationalManagement.api.dto.request.AssignmentRequest;
import com.riwi.educationalManagement.api.dto.response.CompleteAssignmentInfoResponse;

public interface IAssignmentService extends CrudServices<AssignmentRequest, CompleteAssignmentInfoResponse, Long>{
}
