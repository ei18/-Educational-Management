package com.riwi.educationalManagement.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.educationalManagement.api.dto.request.EnrollmentRequest;
import com.riwi.educationalManagement.api.dto.response.EnrollmentResponse;
import com.riwi.educationalManagement.infraestructure.abstract_service.IEnrollmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/enrollments")
@AllArgsConstructor
@Tag(name = "Enrollment")
public class EnrollmentController {
    @Autowired
    private final IEnrollmentService enrollmentService;

    @Operation(
        summary = "List all enrollments with pagination",
        description = "You must submit the page and the page size to get all the corresponding enrollments")
    @GetMapping
    public ResponseEntity<Page<EnrollmentResponse>> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size){

        return  ResponseEntity.ok(this.enrollmentService.getAll(page -1, size));
    }

    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @Operation(
        summary = "List a enrollment by id",
        description = "You must send the id of the enrollment to search for")     
    @GetMapping(path = "{id}")
    public ResponseEntity<EnrollmentResponse> get(@PathVariable Long id){

        return ResponseEntity.ok(this.enrollmentService.get(id));
    }
  
    @Operation(
        summary = "Create a enrollment",
        description = "Create a enrollment")  
    @PostMapping
    public ResponseEntity<EnrollmentResponse> create(@Validated @RequestBody EnrollmentRequest request){

        return ResponseEntity.ok(this.enrollmentService.create(request));
    }

    @ApiResponse(responseCode = "400", description = "When the request is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Update a enrollment",
        description = "Update a enrollment")  
    @PutMapping(path = "{id}")
    public ResponseEntity<EnrollmentResponse> update(@Validated @RequestBody EnrollmentRequest request, @PathVariable Long id){

        return ResponseEntity.ok(this.enrollmentService.update(request, id));
    }

    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Delete a enrollment by id",
        description = "Delete a enrollment by id")  
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        this.enrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

