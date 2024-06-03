package com.riwi.educationalManagement.infraestructure.service;

import com.riwi.educationalManagement.api.dto.request.MessageRequest;
import com.riwi.educationalManagement.api.dto.response.MessageResponse;
import com.riwi.educationalManagement.api.dto.response.UserInfoResponse;
import com.riwi.educationalManagement.domain.entities.Course;
import com.riwi.educationalManagement.domain.entities.Enrollment;
import com.riwi.educationalManagement.domain.entities.Lesson;
import com.riwi.educationalManagement.domain.entities.Message;
import com.riwi.educationalManagement.domain.entities.User;
import com.riwi.educationalManagement.domain.repositories.MessageRepository;
import com.riwi.educationalManagement.domain.repositories.UserRepository;
import com.riwi.educationalManagement.infraestructure.abstract_service.IMessageService;
import com.riwi.educationalManagement.utils.exception.BadRequestException;
import com.riwi.educationalManagement.utils.message.ErrorMessages;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class MessageService implements IMessageService{
    @Autowired
    private final MessageRepository messageRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public MessageResponse create(MessageRequest request) {
        User userSender = userRepository.findById(request.getSenderId()).orElseThrow(() -> new BadRequestException(ErrorMessages.IdNotFound("User")));

        User userReceiver = userRepository.findById(request.getReceiverId()).orElseThrow(() -> new BadRequestException(ErrorMessages.IdNotFound("User")));

        return entityToResponse(messageRepository.save(requestToEntity(request, userSender, userReceiver)));
    }

    @Override
    public MessageResponse get(Long id) {
        return this.entityToResponse(this.find(id));
    }

    @Override
    public Page<MessageResponse> getAll(int page, int size) {
          if (page < 0) page = 0;
        PageRequest pagination = PageRequest.of(page, size);

        return this.messageRepository.findAll(pagination).map(this::entityToResponse);
    }

    @Override
    public MessageResponse update(MessageRequest request, Long id) {
        // Message message = this.find(id);
        // Message messageUpdate = this.requestToEntity(request);
        // messageUpdate.setId(id);

        // return this.entityToResponse(this.messageRepository.save(messageUpdate));

        return null;
    }

    @Override
    public void delete(Long id) {
        this.messageRepository.delete(this.find(id));
    }

    @Override
    public List<MessageResponse> findAllMessageBySenderIdAndReceiverId(Long senderId, Long receiverId){
        return entityToResponseList(messageRepository.findAllByMessageSenderAndMessageReceiver(
            UserInfoResponse.builder()
                    .id(senderId)
                    .build(),
            UserInfoResponse.builder()
                    .id(receiverId)
                    .build()));
    }

    private MessageResponse entityToResponse(Message entity){
        return MessageResponse.builder()
                .id(entity.getId())
                .sentDate(entity.getSentDate())
                .messageContent(entity.getMessageContent())
                .userReceiver(UserInfoResponse.builder()
                        .id(entity.getMessageSender().getId())
                        .username(entity.getMessageSender().getUsername())
                        .email(entity.getMessageSender().getEmail())
                        .fullName(entity.getMessageSender().getFullName())
                        .role(entity.getMessageSender().getRole())
                        .build())
                .userReceiver(UserInfoResponse.builder()
                        .id(entity.getMessageReceiver().getId())
                        .username(entity.getMessageReceiver().getUsername())
                        .email(entity.getMessageReceiver().getEmail())
                        .fullName(entity.getMessageReceiver().getFullName())
                        .role(entity.getMessageReceiver().getRole())
                        .build())
                .build();                
    }

    private Message requestToEntity(MessageRequest request, User userSender, User userReceiver){
        return Message.builder()
                .messageContent(request.getMessageContent())
                .sentDate(LocalDateTime.now())
                .roleSender(userSender.getRole().name())
                .messageSender(userSender)
                .messageReceiver(userReceiver)
                .course(Course.builder()
                        .id(request.getCourseId())
                        .build())
                .build();        
    }

    private List<MessageResponse> entityToResponseList(List<Message> entity){
        return entity.stream()
                .map(message -> MessageResponse.builder()
                        .id(message.getId())
                        .messageContent(message.getMessageContent())
                        .userReceiver(UserInfoResponse.builder()
                            .id(message.getMessageReceiver().getId())
                            .fullName(message.getMessageReceiver().getFullName())
                            .build())
                        .userSender(UserInfoResponse.builder()
                            .id(message.getMessageSender().getId())
                            .fullName(message.getMessageSender().getFullName())
                            .build())
                        .sentDate(message.getSentDate())
                        .build()).toList();
    }

    private Message find(Long id) {
        return this.messageRepository.findById(id).orElseThrow(() -> new BadRequestException(ErrorMessages.IdNotFound("Message")));
    }
}
