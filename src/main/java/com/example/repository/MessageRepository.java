package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Message entity
 * Extends JpaRepository to inherit common database operations
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    
    /**
     * Find all messages posted by a specific account
     * 
     * @param postedBy the account ID that posted the messages
     * @return a list of messages posted by the account
     */
    List<Message> findByPostedBy(Integer postedBy);
}