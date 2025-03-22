package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Account entity
 * Extends JpaRepository to inherit common database operations
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    
    /**
     * Find an account by username
     * 
     * @param username the username to search for
     * @return an Optional containing the account if found
     */
    Optional<Account> findByUsername(String username);
    
    /**
     * Find an account by username and password (for login)
     * 
     * @param username the username to search for
     * @param password the password to match
     * @return an Optional containing the account if found
     */
    Optional<Account> findByUsernameAndPassword(String username, String password);
    
    /**
     * Check if an account exists with the given username
     * 
     * @param username the username to check
     * @return true if an account with the username exists
     */
    boolean existsByUsername(String username);
}