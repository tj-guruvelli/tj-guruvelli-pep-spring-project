package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling Message-related operations
 */
@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    /**
     * Creates a new message
     * 
     * @param message the message to be created
     * @return the created message or null if invalid
     */
    public Message createMessage(Message message) {
        // Validate message text is not empty and within character limit
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty() || 
            message.getMessageText().length() > 255) {
            return null;
        }
        
        return messageRepository.save(message);
    }
    
    /**
     * Retrieves all messages
     * 
     * @return list of all messages
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    
    /**
     * Retrieves a message by its ID
     * 
     * @param id the message ID
     * @return the message with the specified ID, or null if not found
     */
    public Message getMessageById(Integer id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        return optionalMessage.orElse(null);
    }
    
    /**
     * Retrieves all messages posted by a specific account
     * 
     * @param accountId the account ID
     * @return list of messages posted by the specified account
     */
    public List<Message> getMessagesByAccount(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
    
    /**
     * Deletes a message with the specified ID
     * 
     * @param messageId the message ID
     * @return 1 if the message was deleted, 0 otherwise
     */
    public Integer deleteMessage(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }
    
    /**
     * Updates the text of a message with the specified ID
     * 
     * @param messageId the message ID
     * @param newText the new message text
     * @return 1 if message was updated, or null if the message was not found or the new text is invalid
     */
    public Integer updateMessageText(Integer messageId, String newText) {
        // Validate new text
        if (newText == null || newText.trim().isEmpty() || newText.length() > 255) {
            return null;
        }
        
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessageText(newText);
            messageRepository.save(message);
            return 1;
        }
        
        return 0;
    }
}