package com.riwi.educationalManagement.api.controller;

import com.riwi.educationalManagement.api.dto.request.AssignmentRequest;
import com.riwi.educationalManagement.api.dto.response.CompleteAssignmentInfoResponse;
import com.riwi.educationalManagement.infraestructure.abstract_service.IAssignmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/assignments")
@AllArgsConstructor
@Tag(name = "Assignment")
public class AssignmentController {
    @Autowired
    private final IAssignmentService assignmentService;

    @Operation(
        summary = "List all assignments with pagination",
        description = "You must submit the page and the page size to get all the corresponding assignments")
    @GetMapping
    public ResponseEntity<Page<CompleteAssignmentInfoResponse>> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
        return  ResponseEntity.ok(this.assignmentService.getAll(page -1, size));
    }

    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @Operation(
        summary = "List a assignment by id",
        description = "You must send the id of the assignment to search for")     
    @GetMapping(path = "{id}")
    public ResponseEntity<CompleteAssignmentInfoResponse> get(@PathVariable Long id){
        return ResponseEntity.ok(this.assignmentService.get(id));
    }

    @Operation(
        summary = "Create a assignment",
        description = "Create a assignment")  
    @PostMapping
    public ResponseEntity<CompleteAssignmentInfoResponse> create(@Validated @RequestBody AssignmentRequest request){

        return ResponseEntity.ok(this.assignmentService.create(request));
    }

    @ApiResponse(responseCode = "400", description = "When the request is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Update a assignment",
        description = "Update a assignment")  
    @PutMapping(path = "{id}")
    public ResponseEntity<CompleteAssignmentInfoResponse> update(@Validated @RequestBody AssignmentRequest request, @PathVariable Long id){

        return ResponseEntity.ok(this.assignmentService.update(request, id));
    }
    
    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Delete a assignment by id",
        description = "Delete a assignment by id")  
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        this.assignmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
