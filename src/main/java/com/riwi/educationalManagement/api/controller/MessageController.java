package com.riwi.educationalManagement.api.controller;

import java.util.List;

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

import com.riwi.educationalManagement.api.dto.request.MessageRequest;
import com.riwi.educationalManagement.api.dto.response.MessageResponse;
import com.riwi.educationalManagement.infraestructure.abstract_service.IMessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/messages")
@AllArgsConstructor
@Tag(name = "Message")
public class MessageController {
    @Autowired
    private final IMessageService messageService;

    @Operation(
        summary = "List all messages with pagination",
        description = "You must submit the page and the page size to get all the corresponding messages")
    @GetMapping
    public ResponseEntity<Page<MessageResponse>> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size){

        return  ResponseEntity.ok(this.messageService.getAll(page -1, size));
    }

    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    @Operation(
        summary = "List a message by id",
        description = "You must send the id of the message to search for")     
    @GetMapping(path = "{id}")
    public ResponseEntity<MessageResponse> get(@PathVariable Long id){

        return ResponseEntity.ok(this.messageService.get(id));
    }

    @Operation(
        summary = "Create a message",
        description = "Create a message")  
    @PostMapping
    public ResponseEntity<MessageResponse> create(@Validated @RequestBody MessageRequest request){

        return ResponseEntity.ok(this.messageService.create(request));
    }

    @ApiResponse(responseCode = "400", description = "When the request is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Update a message",
        description = "Update a message")  
    @PutMapping(path = "{id}")
    public ResponseEntity<MessageResponse> update(@Validated @RequestBody MessageRequest request, @PathVariable Long id){

        return ResponseEntity.ok(this.messageService.update(request, id));
    }

    @ApiResponse(responseCode = "400", description = "When the id is invalid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(
        summary = "Delete a message by id",
        description = "Delete a message by id")  
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        this.messageService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Find all message by sender id y receiver id",
        description = "Find all message by sender id y receiver id")  
    @GetMapping(path = "/findMessage")
    public ResponseEntity<List<MessageResponse>> findAllMessageBySenderIdAndReceiverId(
            @Validated
            @RequestParam(name = "sender_id") Long senderId,
            @RequestParam(name = "receiver_id") Long receiverId){

        return ResponseEntity.ok(this.messageService.findAllMessageBySenderIdAndReceiverId(senderId, receiverId));
    }
}
