package com.riwi.educationalManagement.domain.repositories;

import com.riwi.educationalManagement.domain.entities.Message;
import com.riwi.educationalManagement.domain.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
     List<Message> findAllByMessageSenderAndMessageReceiver(User userSender, User userReceiver);
}
