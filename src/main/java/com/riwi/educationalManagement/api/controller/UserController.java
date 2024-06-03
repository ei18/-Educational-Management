package com.riwi.educationalManagement.api.controller;

import com.riwi.educationalManagement.api.dto.request.UserRequest;
import com.riwi.educationalManagement.api.dto.response.UserResponse;
import com.riwi.educationalManagement.infraestructure.abstract_service.IUserService;

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
@RequestMapping(path = "/users")
@AllArgsConstructor
@Tag(name = "User")
public class UserController {
    @Autowired
    private final IUserService userService;

    @Operation(
        summary = "List all users with pagination",
        description = "You must submit the page and the page size to get all the corresponding users")
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
        return  ResponseEntity.ok(this.userService.getAll(page -1, size));
    }

    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @Operation(
        summary = "List a user by id",
        description = "You must send the id of the user to search for")     
    @GetMapping(path = "{id}")
    public ResponseEntity<UserResponse> get(@PathVariable Long id){
        return ResponseEntity.ok(this.userService.get(id));
    }

    @Operation(
        summary = "Create a user",
        description = "Create a user")  
    @PostMapping
    public ResponseEntity<UserResponse> create(@Validated @RequestBody UserRequest request){
        return ResponseEntity.ok(this.userService.create(request));
    }

    @ApiResponse(responseCode = "400", description = "When the request is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Update a user",
        description = "Update a user")  
    @PutMapping(path = "{id}")
    public ResponseEntity<UserResponse> update(@Validated @RequestBody UserRequest request, @PathVariable Long id){
        return ResponseEntity.ok(this.userService.update(request, id));
    }

    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Delete a user by id",
        description = "Delete a user by id")  
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
