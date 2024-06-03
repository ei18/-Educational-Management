package com.riwi.educationalManagement.api.controller;

import com.riwi.educationalManagement.api.dto.request.LessonRequest;
import com.riwi.educationalManagement.api.dto.response.CompleteLessonInformationResponse;
import com.riwi.educationalManagement.infraestructure.abstract_service.ILessonService;

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
@RequestMapping(path = "/lessons")
@AllArgsConstructor
@Tag(name = "Lesson")
public class LessonsController {
    @Autowired
    private final ILessonService lessonService;

    @Operation(
        summary = "List all lessons with pagination",
        description = "You must submit the page and the page size to get all the corresponding lessons")
    @GetMapping
    public ResponseEntity<Page<CompleteLessonInformationResponse>> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
        return  ResponseEntity.ok(this.lessonService.getAll(page -1, size));
    }

    
    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @Operation(
        summary = "List a lesson by id",
        description = "You must send the id of the lesson to search for")     
    @GetMapping(path = "{id}")
    public ResponseEntity<CompleteLessonInformationResponse> get(@PathVariable Long id){
        return ResponseEntity.ok(this.lessonService.get(id));
    }

    @Operation(
        summary = "Create a lesson",
        description = "Create a lesson")  
    @PostMapping
    public ResponseEntity<CompleteLessonInformationResponse> create(@Validated @RequestBody LessonRequest request){
        return ResponseEntity.ok(this.lessonService.create(request));
    }

    @ApiResponse(responseCode = "400", description = "When the request is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Update a lesson",
        description = "Update a lesson")  
    @PutMapping(path = "{id}")
    public ResponseEntity<CompleteLessonInformationResponse> update(@Validated @RequestBody LessonRequest request, @PathVariable Long id){
        return ResponseEntity.ok(this.lessonService.update(request, id));
    }

    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Delete a lesson by id",
        description = "Delete a lesson by id")  
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.lessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
