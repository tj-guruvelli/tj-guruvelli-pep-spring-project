package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class that handles HTTP requests for the social media application.
 * Implements endpoints for account registration, login, and message management.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private MessageService messageService;

    /**
     * Register a new user account
     * 
     * @param account The account information to register
     * @return ResponseEntity containing the registered account or error status
     */
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        Account registeredAccount = accountService.register(account);
        
        if (registeredAccount == null) {
            // Check specific error cases
            if (account.getUsername() == null || account.getUsername().trim().isEmpty() || 
                account.getPassword() == null || account.getPassword().length() < 4) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            } else {
                // Username already exists
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(registeredAccount);
    }

    /**
     * Login with existing credentials
     * 
     * @param account The account credentials for login
     * @return ResponseEntity containing the account if login successful or error status
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account loggedInAccount = accountService.login(account.getUsername(), account.getPassword());
        
        if (loggedInAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(loggedInAccount);
    }

    /**
     * Create a new message
     * 
     * @param message The message to create
     * @return ResponseEntity containing the created message or error status
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        
        if (createdMessage == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(createdMessage);
    }

    /**
     * Get all messages
     * 
     * @return List of all messages
     */
    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    /**
     * Get a specific message by ID
     * 
     * @param messageId The ID of the message to retrieve
     * @return The message if found or null
     */
    @GetMapping("/messages/{messageId}")
    public Message getMessageById(@PathVariable Integer messageId) {
       return messageService.getMessageById(messageId);
    }

    /**
     * Delete a message by ID
     * 
     * @param messageId The ID of the message to delete
     * @return The number of rows affected (1 if deleted, 0 if not found)
     */
    @DeleteMapping("/messages/{messageId}")
    public Integer deleteMessageById(@PathVariable Integer messageId) {
        return messageService.deleteMessage(messageId);
    }

    /**
     * Update a message text by ID
     * 
     * @param messageId The ID of the message to update
     * @param updatedMessage The message containing new message text
     * @return ResponseEntity with the number of rows affected or error status
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageText(
            @PathVariable Integer messageId, 
            @RequestBody Message updatedMessage) {
        
        Integer result = messageService.updateMessageText(messageId, updatedMessage.getMessageText());
        
        if (result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Get all messages by a specific user
     * 
     * @param accountId The ID of the user account
     * @return List of messages by the specified user
     */
    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getMessagesByAccount(@PathVariable Integer accountId) {
        return messageService.getMessagesByAccount(accountId);
    }
}