package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class that handles business logic for Account operations
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    /**
     * Register a new account
     * 
     * @param account The account information to register
     * @return The registered account or null if registration fails
     */
    public Account register(Account account) {
        // Validate username is not blank
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            return null;
        }
        
        // Validate password is at least 4 characters
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }
        
        // Check if username already exists
        if (accountRepository.existsByUsername(account.getUsername())) {
            // Return null with a special case - caller will check for duplicate username
            return null;
        }
        
        // Save and return the new account
        return accountRepository.save(account);
    }
    
    /**
     * Login with existing credentials
     * 
     * @param username The username for login
     * @param password The password for login
     * @return The account if login successful or null
     */
    public Account login(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password).orElse(null);
    }
    
    /**
     * Find an account by ID
     * 
     * @param accountId The ID of the account to find
     * @return The account if found or null
     */
    public Account findById(Integer accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }
    
    /**
     * Check if an account exists by ID
     * 
     * @param accountId The ID of the account to check
     * @return true if the account exists
     */
    public boolean existsById(Integer accountId) {
        return accountRepository.existsById(accountId);
    }
    
    /**
     * Check if an account exists by username
     * 
     * @param username The username to check
     * @return true if an account with the username exists
     */
    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }
}