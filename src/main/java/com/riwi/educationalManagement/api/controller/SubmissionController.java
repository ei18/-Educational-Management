package com.riwi.educationalManagement.api.controller;

import com.riwi.educationalManagement.api.dto.request.SubmissionRequest;
import com.riwi.educationalManagement.api.dto.response.SubmissionResponse;
import com.riwi.educationalManagement.infraestructure.abstract_service.ISubmissionService;

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
@RequestMapping(path = "/submissions")
@AllArgsConstructor
@Tag(name = "Submission")
public class SubmissionController {
    @Autowired
    private final ISubmissionService submissionService;

    @Operation(
        summary = "List all submissions with pagination",
        description = "You must submit the page and the page size to get all the corresponding submissions")
    @GetMapping
    public ResponseEntity<Page<SubmissionResponse>> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
        return  ResponseEntity.ok(this.submissionService.getAll(page -1, size));
    }

    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @Operation(
        summary = "List a submission by id",
        description = "You must send the id of the submission to search for")     
    @GetMapping(path = "{id}")
    public ResponseEntity<SubmissionResponse> get(@PathVariable Long id){
        return ResponseEntity.ok(this.submissionService.get(id));
    }

    @Operation(
        summary = "Create a submission",
        description = "Create a submission")  
    @PostMapping
    public ResponseEntity<SubmissionResponse> create(@Validated @RequestBody SubmissionRequest request){
        return ResponseEntity.ok(this.submissionService.create(request));
    }

    @ApiResponse(responseCode = "400", description = "When the request is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Update a submission",
        description = "Update a submission")  
    @PutMapping(path = "{id}")
    public ResponseEntity<SubmissionResponse> update(@Validated @RequestBody SubmissionRequest request, @PathVariable Long id){
        return ResponseEntity.ok(this.submissionService.update(request, id));
    }

    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Delete a submission by id",
        description = "Delete a submission by id")  
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.submissionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
