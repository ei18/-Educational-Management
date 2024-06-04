package com.riwi.educationalManagement.api.controller;

import com.riwi.educationalManagement.api.dto.request.CourseRequest;
import com.riwi.educationalManagement.api.dto.response.CourseAndUsersResponse;
import com.riwi.educationalManagement.api.dto.response.CourseToUserResponse;
import com.riwi.educationalManagement.domain.entities.Course;
import com.riwi.educationalManagement.infraestructure.abstract_service.ICourseService;
import com.riwi.educationalManagement.infraestructure.abstract_service.IEnrollmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/courses")
@AllArgsConstructor
@Tag(name = "Course")
public class CourseController {
    @Autowired
    private final ICourseService courseService;
    @Autowired
    private final IEnrollmentService enrollmentService;

    @Operation(
        summary = "List all courses with pagination",
        description = "You must submit the page and the page size to get all the corresponding courses")
    @GetMapping
    public ResponseEntity<Page<CourseToUserResponse>> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
        return  ResponseEntity.ok(this.courseService.getAll(page -1, size));
    }

    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @Operation(
        summary = "List a course by id",
        description = "You must send the id of the course to search for")     
    @GetMapping(path = "/{id}")
    public ResponseEntity<CourseToUserResponse> get(@PathVariable Long id){
        return ResponseEntity.ok(this.courseService.get(id));
    }

    @Operation(
        summary = "Create a course",
        description = "Create a course")  
    @PostMapping
    public ResponseEntity<CourseToUserResponse> create(@Validated @RequestBody CourseRequest request){
        return ResponseEntity.ok(this.courseService.create(request));
    }

    @ApiResponse(responseCode = "400", description = "When the request is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Update a course",
        description = "Update a course")  
    @PutMapping(path = "/{id}")
    public ResponseEntity<CourseToUserResponse> update(
            @Validated
            @RequestBody CourseRequest request,
            @PathVariable Long id){

        return ResponseEntity.ok(this.courseService.update(request, id));
    }

    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Delete a course by id",
        description = "Delete a course by id")  
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Get all users by course ID",
        description = "Get all users by course ID")  
    @GetMapping(path = "/{course_id}/users")
    public ResponseEntity<CourseAndUsersResponse> getAllUserByCourseId(
            @Validated
            @PathVariable(name = "course_id") Long courseId){

        CourseAndUsersResponse courseAndUsersResponse =  this.enrollmentService.getAllUserByCourseId(courseId);
        if (courseAndUsersResponse.getId() == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(this.enrollmentService.getAllUserByCourseId(courseId));
    }

    @Operation(
        summary = "Get all courses by name",
        description = "Get all courses by name")  
    @GetMapping(path = "/courseName/{courseName}")
    public ResponseEntity<List<Course>> getAllCourseByCourseName(
            @PathVariable String courseName){
        return ResponseEntity.ok(this.courseService.findAllByCourseName(courseName));
    }
}
